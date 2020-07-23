package com.bmft.servlet;


import com.bmft.pojo.User;
import com.bmft.utils.SendEmailUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String mailbox = req.getParameter("mailbox");
        User user = new User(username,password,mailbox);
        Thread thread = new Thread(new SendEmailUtil(user));
        thread.start();
        System.out.println("---邮件已发送请查收----");
        req.setAttribute("message","邮件已发送请查收");
        req.getRequestDispatcher("index.jsp").forward(req,resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
