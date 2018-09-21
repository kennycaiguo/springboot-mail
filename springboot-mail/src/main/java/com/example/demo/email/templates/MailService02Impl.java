package com.example.demo.email.templates;

import java.io.File;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * 邮件服务类
 * @author midMoonNight
 *
 */
@Service
public class MailService02Impl implements MailService02 {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.address}")
    private String from;
    
    /**
     * 发送简单邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     */
    @Override
	public void sendSimpleEmail(String to, String subject, String content) {
    	//创建简单邮件消息
    	SimpleMailMessage message = new SimpleMailMessage();
    	//设置发送人
        message.setFrom(from);
        //设置收件人
        message.setTo(to);

        /* String[] adds = {"xxx@qq.com","yyy@qq.com"}; //同时发送给多人
        message.setTo(adds);*/
        //设置主题
        message.setSubject(subject);
        //设置内容
        message.setText(content);
        try {
        	//执行发送邮件
            mailSender.send(message);
            logger.info("简单邮件已经发送。");
        } catch (Exception e) {
            logger.error("发送简单邮件时发生异常！", e);
        }
	}

    /**
     * 发送html格式邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     */
	@Override
	public void sendHtmlEmail(String to, String subject, String content) {
		//创建一个MINE消息
		MimeMessage message = mailSender.createMimeMessage();

        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("发送html邮件时发生异常！", e);
        }
	}
	/**
     * 发送带附件的邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
	@Override
	public void sendAttachmentsEmail(String to, String subject, String content, String filePath) {
		MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // true表示这个邮件是有附件的
            helper.setText(content, true);
            //创建文件系统资源
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            //添加附件
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送带附件的邮件时发生异常！", e);
        }
	}
	/**
     * 发送带静态资源的邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     * @param rscPath  静态资源链接
     * @param rscId    静态资源id
     */
	@Override
	public void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId) {
		MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            //添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res) 来实现
            helper.addInline(rscId, res);

            mailSender.send(message);
            logger.info("嵌入静态资源的邮件已经发送。");
        } catch (MessagingException e) {
            logger.error("发送嵌入静态资源的邮件时发生异常！", e);
        }
	}
	/**
     * 发送thymeleaf模板的简单邮件
     * @param to 接受者
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendTemplateMail(String to, String subject, String content) {
        MimeMessage message=mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            mailSender.send(message);
            System.out.println("html格式邮件发送成功");
        }catch (Exception e){
            System.out.println("html格式邮件发送失败");
        }
    }

    /**
     * 发送thymeleaf模板的添加附件的邮件
     * @param to 接受者
     * @param subject 主题
     * @param content 内容
     */
	@Override
	public void sendTemplateMailWithAccessory(String to, String subject, String content,List<String> paths) {
		MimeMessage message=mailSender.createMimeMessage();
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            
            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            //在模版html中放置一张图片
            FileSystemResource avatar = new FileSystemResource(new File(paths.get(0)));
            //<img src="cid:avatar" />
            helper.addInline("avatar", avatar);
            
            for (String string : paths) {
            	FileSystemResource fileSystemResource=new FileSystemResource(new File(string));           	
                helper.addAttachment("图片.jpg",fileSystemResource);
			}
            mailSender.send(message);
            System.out.println("html格式邮件发送成功");
        }catch (Exception e){
            System.out.println("html格式邮件发送失败");
        }
	}
}
