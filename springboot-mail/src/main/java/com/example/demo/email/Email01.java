package com.example.demo.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
/**
 * 简单的发送邮件
 * @author midMoonNight
 *
 */
@Component
public class Email01 {
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendSimpleMail() {
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
            //"图片.jpg"，“图片”是附件在邮件中的名称，“.jpg”是附件的类型
            helper.addAttachment("图片.jpg",fileSystemResource);
            javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
