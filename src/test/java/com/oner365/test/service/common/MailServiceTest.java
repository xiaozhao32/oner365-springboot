package com.oner365.test.service.common;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.oner365.common.config.properties.DefaultFileProperties;
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
  private JavaMailSender sender;

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

  @Disabled
  void testEmail() {
    String to = "zhaoyong@oner365.com";
    String subject = "test";
    String attachmentFile = "/1.jpg";
    
    Context context = new Context();
    context.setVariable("username", to);
    String emailContent = templateEngine.process("/mail_template", context);

    MimeMessage message = sender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(emailContent, true);
      
      File file = new File(fileProperties.getDownload() + attachmentFile);
      helper.addAttachment(file.getName(), file);
    } catch (MessagingException e) {
      logger.error("mail error", e);
    }
    sender.send(message);
  }
}
