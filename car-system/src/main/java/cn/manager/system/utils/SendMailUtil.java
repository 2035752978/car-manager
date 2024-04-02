package cn.manager.system.utils;



import cn.manager.system.config.MailProperties;
import com.sun.mail.util.MailSSLSocketFactory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;

import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


@Slf4j
@Component
@EnableScheduling
public class SendMailUtil {

    @Autowired
    private MailProperties mailProperties;
    @Value("${mail.sender-mail}")
    public String senderMail;
    @Value("${mail.host}")
    public String mailHost;
    @Value("${mail.password}")
    public String password;


    /**
     * 发送邮件
     * @param pids 图片 id
     *
     */
    @Async
    public void sendMail(String mail,String carNum,Integer area,Integer money) throws MessagingException, GeneralSecurityException {
//        String senderMail = mailProperties.getSenderMail();         // 发件人电子邮箱
//        String mailHost = mailProperties.getHost();                 // 指定发送邮件的主机
//        String password = mailProperties.getPassword();             // 发件邮箱的密钥授权码

        String senderMail="";
        String mailHost="smtp.163.com";
        String password="111";
        // 获取系统属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", mailHost);
        properties.put("mail.smtp.auth", "true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        // 获取默认 session 对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {

            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                //发件人邮件用户名、授权码
//                return new PasswordAuthentication("heiheiduan02@163.com", "UYPDIGARZZAKUDXT");
                return new PasswordAuthentication(senderMail, password);
            }

        });
        // 创建默认的 MimeMessage 对象
        MimeMessage message = new MimeMessage(session);
        // Set From: 头部头字段
        message.setFrom(new InternetAddress(senderMail));
        // Set To: 头部头字段
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        // Set Subject: 头部头字段
        String sub = "校园车辆违规提醒";
//        picHost = "http://localhost:8080";
        message.setSubject(sub);
        String text = "";
        // 设置消息体
        text = "尊敬的用户您好，您的车牌号为："+carNum+"的车辆在学校"+area+"区域存在违规行为，已经产生"+money+"元的违规费用，请及时处理，谢谢！";
        message.setText(text);
        // 发送消息
        Transport.send(message);
        log.info("发送邮件：\tfrom: {}\tto:{}\tsub: {}\ttext: {}\t邮件发送成功....",  senderMail,mail, sub, text);
    }






    public static void main(String[] args) {
        SendMailUtil sendMailUtil = new SendMailUtil();
        try {
            sendMailUtil.sendMail("111@qq.com","粤B12345",1,100);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    }
