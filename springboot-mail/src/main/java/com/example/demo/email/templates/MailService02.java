package com.example.demo.email.templates;

import java.util.List;

/**
 * 邮件服务接口
 * @author midMoonNight
 *
 */
public interface MailService02 {

	/**
     * 发送简单邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     */
    public void sendSimpleEmail(String to, String subject, String content);
    /**
     * 发送html格式邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     */
    public void sendHtmlEmail(String to, String subject, String content);
    /**
     * 发送带附件的邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
    public void sendAttachmentsEmail(String to, String subject, String content, String filePath);
    /**
     * 发送带静态资源的邮件
     * @param to       接受者
     * @param subject  主题
     * @param content  内容
     * @param rscPath  静态资源链接
     * @param rscId    静态资源id
     */
    public void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId);	
	/**
     * 发送thymeleaf模板的简单邮件
     * @param to 接受者
     * @param subject 主题
     * @param content 内容
     */
    void sendTemplateMail(String to,String subject,String content);   
    /**
     * 发送thymeleaf模板， 模板里添加图片的，带 附件的邮件
     * @param to 接受者
     * @param subject 主题
     * @param content 内容
     * @param paths 附件
     */
    void sendTemplateMailWithAccessory(String to,String subject,String content,List<String> paths);
}
