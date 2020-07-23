package com.file.servlet;

import com.file.util.UploadFileUtil;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UtilTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        try {
            UploadFileUtil uploadFileUtil = new UploadFileUtil(req, resp);
            boolean b = uploadFileUtil.uploadFile();
            if (b) {
                //如果成功
                req.setAttribute("test","true");
                req.getRequestDispatcher("index.jsp").forward(req,resp);
            }else {
                //如果失败
                req.setAttribute("test","false");
                req.getRequestDispatcher("index.jsp").forward(req,resp);
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
