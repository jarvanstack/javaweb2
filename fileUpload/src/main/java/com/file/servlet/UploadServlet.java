package com.file.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class UploadServlet extends HttpServlet {
    //service是必须走的一个
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //验证是否已 post并且 enctype="multipart/..."
        boolean isMutipart = ServletFileUpload.isMultipartContent(req);
        if (!isMutipart) {
            //如果不 post并且 enctype="multipart/..." 就不是上传
            return;//退出方法
        }
        try {
            //fileItem 是表单中每一个元素比如params的封装，因为不能使用req.getParameter()
            //创建一个 fileItem工厂类 (工厂模式)
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建一个文件上传处理器（装饰设计模式）
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解析请求 -- 相当于 req.getParameter()
            List<FileItem> fileItems = upload.parseRequest(req);

            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    //如果是普通控件
                }else {
                    //如果是上传文件的控件
                    String savePath = "C:/Users/25301/Desktop/";
                    String folderName = UUID.randomUUID().toString();
                    String fileName = fileItem.getName();
                    //写入文件
                    fileItem.write(new File(savePath+folderName+"/",fileName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}