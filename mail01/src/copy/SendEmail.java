package copy;

import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.Properties;

public class SendEmail {
    @Test//这个方法可以获取项目src上级路径
    public void testPath9(){
        String modelPath=System.getProperty("user.dir");
        System.out.println(modelPath);
    }
    String messageType = "related"; // related 就是 img+HTML，mixed就是加上附件
    public static void main(String[] args) throws Exception {

        SendEmail sendEmail = new SendEmail();
        sendEmail.messageType = "mixed";
        sendEmail.test();
    }
    /**
     * 发送第三种信息.+图片+附件
     * @param message
     * @return
     * @throws MessagingException
     */
    public MimeMessage message03(MimeMessage message) throws MessagingException {
        message.setSubject("信息2 HTML+图片+附件","utf8");
        //1. 图片部分 imgBodyPart
        //准备图片数据  MIME多用途互联网邮件拓展(Multipurpose Internet Mail Extensions)
        MimeBodyPart imgBodyPart = new MimeBodyPart();
        //图片需要进过数据处理，这里处理数据
        String modelPath=System.getProperty("user.dir");//D:\Enviroment\java\javaweb02\mail01
        String imgPath = modelPath+"/mail01/Snipaste_2020-07-23_14-25-43.png";
        DataHandler dataHandler = new DataHandler(new FileDataSource(imgPath));
        imgBodyPart.setDataHandler(dataHandler);//添加数据处理后的图片
        imgBodyPart.setContentID("img01.png");//设置被调用的cid

        //2.html 部分 htmlBodyPart
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        String html = "<h1 style='color:red'>contentHTML信息</h1>" +
                "<img src='cid:img01.png>";
        htmlBodyPart.setContent(html,"text/html;charset=utf8");
        //3. 附件部分 01
        MimeBodyPart atmBodyPart03 = new MimeBodyPart();
        String attachment03 = modelPath+"/mail01/lib/junit-4.10.jar";
        DataHandler dataHandler03 = new DataHandler(new FileDataSource(attachment03));
        atmBodyPart03.setDataHandler(dataHandler03);
        atmBodyPart03.setFileName("junit-4.10.jar");// 图片是id，因为要使用，附件是filename，文件名称
        //4. 附件 4

        MimeBodyPart atmBodyPart04 = new MimeBodyPart();
        String attachment04 = modelPath+"/mail01/lib/activation-1.1.1.jar";
        DataHandler dataHandler04 = new DataHandler(new FileDataSource(attachment04));
        atmBodyPart04.setDataHandler(dataHandler04);
        atmBodyPart04.setFileName("activation-1.1.1.jar");// 图片是id，因为要使用，附件是filename，文件名称

        //组装 bodyPart
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(imgBodyPart);//图片身体部分 1.
        multipart.addBodyPart(htmlBodyPart);//html 身体部分 2.
        multipart.setSubType("related");//附件是 mixed

        //混合MimeMultipart 变为部分Body  htmlAndImg,封装
        MimeBodyPart htmlAndImg = new  MimeBodyPart();
        htmlAndImg.setContent(multipart);

        //将混合改变后的body当做普body加到最后的 混合 finalMultiPart里
        MimeMultipart finalMultipart = new MimeMultipart();
        finalMultipart.addBodyPart(htmlAndImg);//html  和 Img  的混合
        finalMultipart.addBodyPart(atmBodyPart03);//3. 附件
        finalMultipart.addBodyPart(atmBodyPart04);//4. 附件
        finalMultipart.setSubType("mixed");//【这个一定要设置之前忘记了】

        message.setContent(finalMultipart);

        return message;
    }
    /**
     * 发送第二种信息.+图片
     * @param message
     * @return
     * @throws MessagingException
     */
    public MimeMessage message02(MimeMessage message) throws MessagingException {
        message.setSubject("信息2带图片信息的标题","utf8");
        //1. 图片部分
        //准备图片数据  MIME多用途互联网邮件拓展(Multipurpose Internet Mail Extensions)
        MimeBodyPart imgBodyPart = new MimeBodyPart();
        //图片需要进过数据处理，这里处理数据
        String modelPath=System.getProperty("user.dir");//D:\Enviroment\java\javaweb02\mail01
        String imgPath = modelPath+"/mail01/Snipaste_2020-07-23_14-25-43.png";
        DataHandler dataHandler = new DataHandler(new FileDataSource(imgPath));
        imgBodyPart.setDataHandler(dataHandler);//添加数据处理后的图片
        imgBodyPart.setContentID("img01.png");//设置被调用的cid

        //html 部分
        MimeBodyPart htmlBodyPart = new MimeBodyPart();
        String html = "<h1 style='color:red'>contentHTML信息</h1>" +
                "<img src='cid:img01.png>";
        htmlBodyPart.setContent(html,"text/html;charset=utf8");
        //描述数据关系
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(imgBodyPart);//图片身体部分
        multipart.addBodyPart(htmlBodyPart);//html 身体部分
        multipart.setSubType("related");//附件是 mixed
        message.setContent(multipart,"text/html;charset=utf8");

        return message;
    }
    /**
     * 发送第一种信息.
     * @param message
     * @return
     * @throws MessagingException
     */
    public MimeMessage message01(MimeMessage message) throws MessagingException {
        message.setSubject("信息1的标题","utf8");
        message.setContent("<h1 style='color:red'>contentHTML信息</h1>","text/html;charset=utf8");
        return message;
    }

    public void test() throws Exception {
        // 收件人电子邮箱
        String to = "2530196154@qq.com";

        // 发件人电子邮箱
        String from = "2530196154@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2530196154@qq.com", "eawdlpifahyeeafe"); //发件人邮件用户名、密码
            }
        });
        session.setDebug(true);//监控模式打印信息

        // 创建默认的 MimeMessage 对象 MIME多用途互联网邮件拓展(Multipurpose Internet Mail Extensions)
        MimeMessage message = new MimeMessage(session);

        // Set From: 头部头字段
        message.setFrom(new InternetAddress(from));

        // Set To: 头部头字段
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        /**
         *  1.发送普通邮件 HTML 邮件
         */
        if (messageType.equals("related")) {
            message = message01(message);
        } else if (messageType.equals("img")) {
            /**
             * 2. HTML邮件的一种 发送带图片的
             */
            message = message02(message);
        } else if (messageType.equals("mixed")) {
            /**
             * 3.最高级的发送带附件的
             */
            message = message03(message);
        }

        Transport.send(message);
        System.out.println("Sent message successfully....from runoob.com");

    }
}