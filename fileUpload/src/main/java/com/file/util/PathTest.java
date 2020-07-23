package com.file.util;

import org.junit.Test;

import javax.servlet.http.HttpServlet;

public class PathTest extends HttpServlet {
    @Test
    public void test(){
//        System.out.println(this.getServletContext().getRealPath("/WEB-INF/upload/"));
        System.out.println(PathTest.class.getClassLoader().getResource("").toString());
        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
    }
}
