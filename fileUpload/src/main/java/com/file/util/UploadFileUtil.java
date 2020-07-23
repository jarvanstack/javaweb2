package com.file.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 这是一个文件上传工具类，实现用
 * 1. 使用 res,resp 构造
 * 2. 使用 .upload() 方法实现上传，
 * 3. （默认路径，文件名，文件夹，缓冲大小等）但是这些均可设置
 */
public class UploadFileUtil extends HttpServlet {
    private boolean isUploadSucess;//返回是否上传成功
    private boolean isMutipart;//验证是否为 post 和  enctype="multipart/..."
    private DiskFileItemFactory fileItemFactory;//FileItems 工厂模式
    private ServletFileUpload fileUpload ;//文件上传器 装饰设计模式
    private List<FileItem> fileItems;//解析请求表单
    private HashMap<String,Object> parametersMap ;//表单参数map
    private String folderPath;//文件夹路径的路径 默认web-info/upload/
    private String folderName;//文件夹名称默认 UUID随机名称 XXX/
    private String fileName ;//保存的file名称，默认上传的file名称' XXX.png
    private String realSavePath;//
    private int bufferSize;//储存文件的缓冲大小 默认 10*1024
    private String bufferPath;//缓冲文件路径
    private int temSize ;//如果超过了某个大小就是临时文件呢
    private String typeRole;//文件的类型限制

    /**
     * 执行下载文件的操作
     * @return
     */
    public boolean uploadFile() throws IOException {
        if (isMutipart) {//如果表单符合要求才
            Set<String> keySet = parametersMap.keySet();
            for (String s : keySet) {
                FileItem fileItem = (FileItem) parametersMap.get(s);
                if (fileItem==null ||fileItem.isFormField()) {
                    //如果是为空或者普通的字段就执行
                } else {
                    fileName = fileItem.getName();//文件名不是字段名
                    //默认到长久区
                    String parentPathName = (folderPath +"long/"+ folderName ).replace("\\", "/");//绝对保存路径
                    File file = new File(parentPathName,fileName);
                    if (!file.getParentFile().exists()) {
                        //如果不存在就新建dir
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                    System.out.println("保存的绝对路径为" + file.getPath());
                    try {
                        fileItem.write(file);
                        isUploadSucess = true;//这里感觉以后可以优化，多个上传文件怎么弄个？？
                    } catch (Exception e) {
                        e.printStackTrace();
                        isUploadSucess = false;//一个文件遇到异常就上传失败
                    } finally {
                        //结束始终要做点什么
                    }
                }
            }
        }
        return isUploadSucess;
    }
    /**
     * 构造器 字段赋值默认值
     * @param req
     * @param resp
     */
    public  UploadFileUtil(HttpServletRequest req, HttpServletResponse resp) throws FileUploadException {
        isUploadSucess = false;
        folderPath = req.getSession().getServletContext().getRealPath("WEB-INF/upload/");
//        folderPath = this.getServletContext().getRealPath("/WEB-INF/upload/");
        folderName = UUID.randomUUID().toString()+"/";//随机文件名称
        bufferSize = 1024*1024;//缓冲大小 1mb
        temSize = 1024*1024*10;//最大为10MB，不然就临时文件夹
        bufferPath = folderPath+"bufferPath/";
        isMutipart = ServletFileUpload.isMultipartContent(req);

        fileItemFactory = getDiskFileItemFactory();//调用方法
        fileUpload = getServletFileUpload();//调用方法

        fileItems = fileUpload.parseRequest(req);
        parametersMap = new HashMap<String, Object>();
        for (FileItem fileItem : fileItems) {
            parametersMap.put(fileItem.getFieldName(),fileItem);//给map赋值
        }

    }

    /**
     * 设置缓冲文件大小和位置，返回DiskFileItemFactory
     * @return
     */
    public DiskFileItemFactory getDiskFileItemFactory(){
        File bufferPathFile = new File(bufferPath);
        if (!bufferPathFile.exists()) {
            //如果没有就新建一个缓冲路径
            bufferPathFile.mkdirs();
        }
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(bufferSize);//设置缓冲文件大小 1MB
        diskFileItemFactory.setRepository(bufferPathFile);//缓冲的地方
        return diskFileItemFactory;
    }

    /**
     * 获取文件上传器.
     * @return
     */
    public ServletFileUpload getServletFileUpload(){
        ServletFileUpload upload = new ServletFileUpload();
        upload.setFileItemFactory(fileItemFactory);//第二种设置factory的方法，第一种构造时候设置
        upload.setProgressListener(new ProgressListener() {
            public void update(long l, long l1, int i) {
//                System.out.println("总大小:"+l1+"  已经上传："+l+" 最后一个参数是：i:"+i);
            }
        });//进度监听器 --
        upload.setHeaderEncoding("UTF-8");//乱码问题
        upload.setFileSizeMax(temSize);//设置单个文件的最大值，
        return upload;
    }

}
