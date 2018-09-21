package com.example.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.demo.email.templates.MailService02;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMailApplicationTests {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
    private MailService02 mailService;

	@Autowired
    private TemplateEngine templateEngine;

    @Value("${mail.toMail.address}")
    private String to;
    
    
	//发送简单邮件
    @Test
    public void sendSimpleMail() throws Exception {
        mailService.sendSimpleEmail(to,"this is simple mail"," hello PDS");
    }
    //发送带html的邮件
    @Test
    public void sendHtmlMail() throws Exception {
        String content="<html>\n" +
                "<body>\n" +
                "    <h3>hello pds ! 这是一封Html邮件!</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlEmail(to,"this is html mail",content);
    }
    //发送带附件的邮件
    @Test
    public void sendAttachmentsMail() {
        String filePath="C:\\Users\\Administrator\\Desktop\\picture\\a37cfca392c861061a8a65042ab4ef2a.jpg";
        mailService.sendAttachmentsEmail(to, "主题：带附件的邮件", "收到附件，请查收！", filePath);
    }
    //发送带静态资源的邮件
    @Test
    public void sendInlineResourceMail() {
        String rscId = "001";
        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
        String imgPath = "C:\\Users\\Administrator\\Desktop\\picture\\a37cfca392c861061a8a65042ab4ef2a.jpg";

        mailService.sendInlineResourceEmail(to, "主题：这是有图片的邮件", content, imgPath, rscId);
    }
    //thymeleaf模板邮件
    @Test
    public void sendTemplateMail() throws  Exception{
        //创建邮件正文
        //是导这个包import org.thymeleaf.context.Context;
        Context context = new Context();
        context.setVariable("username","PDS");
        //获取thymeleaf的html模板
        String emailContent= templateEngine.process("hello", context);
        mailService.sendTemplateMail(to,"这是thymeleaf模板邮件",emailContent);
    }
    
    
    //thymeleaf模板加附件的邮件
    @Test
    public void sendTemplateMailWithAccessory() {
    	Context context = new Context();
        context.setVariable("username","PDS");
        //获取thymeleaf的html模板
        List<String> paths = new ArrayList<>();
        paths.add("C:\\Users\\Administrator\\Desktop\\picture\\a37cfca392c861061a8a65042ab4ef2a.jpg");
        paths.add("C:\\Users\\Administrator\\Desktop\\picture\\bb2f9e206d6a22188093047fcb1b5342.jpg");
        String emailContent= templateEngine.process("hello", context);
        mailService.sendTemplateMailWithAccessory(to,"这是thymeleaf模板邮件",emailContent,paths);
	}
    
    //测试Email01.java
    @Test
	public void contextLoads() {
		MimeMessage message = null;
			
		try {
			message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setFrom("577950300@qq.com");
            helper.setTo("2911728681@qq.com");
            helper.setSubject("标题：发送Html内容");

            StringBuffer sb = new StringBuffer();
            sb.append("<h1>大标题-h1</h1>")
                    .append("<p style='color:#F00'>红色字</p>")
                    .append("<p style='text-align:right'>右对齐</p>");
            helper.setText(sb.toString(), true);
            FileSystemResource fileSystemResource=new FileSystemResource(new File("C:\\Users\\Administrator\\Desktop\\picture\\a37cfca392c861061a8a65042ab4ef2a.jpg"));
            helper.addAttachment("图片.jpg",fileSystemResource);
            javaMailSender.send(message);
            System.out.println("执行完毕");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	@Test
    public void sendHtmlEmail() throws MessagingException {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        //开启带附件true
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
        Context context = new Context();
        //获取模板html代码
        String process = templateEngine.process("hello", context);

        try {
            messageHelper.setFrom("577950300@qq.com");
            messageHelper.setTo(to);
            messageHelper.setSubject("SpringBootThymeleaf模板邮件");
            messageHelper.setText(process, true);
            FileSystemResource avatar = new FileSystemResource(
                    new File("C:\\Users\\Administrator\\Desktop\\picture\\a37cfca392c861061a8a65042ab4ef2a.jpg"));
            //<img src="cid:avatar" />
            messageHelper.addInline("avatar", avatar);

        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        javaMailSender.send(mailMessage);
    }
}
