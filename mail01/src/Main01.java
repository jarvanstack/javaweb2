
import java.util.Properties;
import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 简单的邮件发送
 * QQ的高级一些
 */
public class Main01 {
    public static void main(String[] args) {
        try {
            new Main01().testQQ();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void testQQ() throws Exception {
        //收件人的邮箱
        String to = "2530196154@qq.com";
        //发件人的邮箱
        String from = "2530196154@qq.com";
        //发送邮件的主机，接受端的服务器QQ服务器
        String host = "smtp.qq.com";//SMTP是接受的协议pop3是发送的协议
        //获取系统属性，这个也可以在配置里properties
        Properties properties = System.getProperties();
        //设置邮件服务器主机 QQ 接受的主机类型
        properties.setProperty("main.smtp.host",host);
        //下面为固定配置可封装
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.auth", "true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //获取默认的session对象,用于发送邮件的邮箱的账号密码.
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2530196154@qq.com", "eawdlpifahyeeafe");
            }
        });
        session.setDebug(true);//设置为调试模式打印
//        Transport transport = session.getTransport();
//        transport.connect("main.smtp.host","2530196154@qq.com", "eawdlpifahyeeafe");
        //创建发送的信息
        MimeMessage message = new MimeMessage(session);
        // Set From: 头部头字段
        message.setFrom(new InternetAddress(from));

        // Set To: 头部头字段
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: 头部头字段
        message.setSubject("你好欢迎使用mail");

        // 设置消息体
        message.setText("<h1>hello mail</h1>");
        //发送

        Transport.send(message);
        System.out.println("finish");

    }
}
