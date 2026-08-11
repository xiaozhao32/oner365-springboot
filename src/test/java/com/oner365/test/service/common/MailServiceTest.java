package com.oner365.test.service.common;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.oner365.data.commons.config.properties.DefaultFileProperties;
import com.oner365.test.service.BaseServiceTest;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * 单元测试 - 邮件
 *
 * @author zhaoyong
 */
@Disabled
@SpringBootTest
class MailServiceTest extends BaseServiceTest {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private TemplateEngine templateEngine;

    @Resource
    private DefaultFileProperties fileProperties;

    @Value("${spring.mail.username}")
    private String from;

    @Test
    void test() {
        Assertions.assertEquals("MailServiceTest", MailServiceTest.class.getSimpleName());
    }

    @Test
    void testEmail() {
        String to = "xx@oner365.com";
        String subject = "test";
        String text = "hello";
        String attachmentFile = "/1.jpg";
        File file = new File(fileProperties.getDownload() + attachmentFile);
        Assertions.assertNotNull(file.exists());

        Context context = new Context();
        context.setVariable("username", to);
        String htmlContent = templateEngine.process("mail_template", context);
        Assertions.assertNotNull(htmlContent);

        // send simple email
        sendSimpleEmail(to, subject, text);
    }

    /**
     * 发送普通文本邮件
     */
    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * 发送HTML邮件
     */
    public void sendHtmlEmail(String to, String subject, String htmlContent, String attachmentFile) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // true 表示邮件支持附件等复杂格式
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            // 第二个参数 true 表示内容是HTML格式
            helper.setText(htmlContent, true);
            File file = new File(attachmentFile);
            helper.addAttachment(file.getName(), file);
            mailSender.send(message);
        }
        catch (MessagingException e) {
            logger.error("mail error", e);
        }
    }

}
