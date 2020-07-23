package com.bmft.utils;

import com.bmft.pojo.User;

public class SendEmailUtilTest {
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("嘉文");
        user.setPassword("dff");
        user.setMailAddress("2530196154@qq.com");
        Thread thread = new Thread(new SendEmailUtil(user));
        thread.start();
        System.out.println("多线程发送");


    }
}
