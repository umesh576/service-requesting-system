package com.example.serviceFinal.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private TemplateEngine templateEngine; // Optional, for HTML templates

  // Method 1: Send Simple Text Email
  public void sendSimpleEmail(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    message.setFrom("your-email@gmail.com"); // Optional

    mailSender.send(message);
    System.out.println("Email sent successfully to " + to);
  }

  // Method 2: Send HTML Email
  public void sendHtmlEmail(String to, String subject, String htmlContent)
    throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlContent, true); // true = isHtml
    helper.setFrom("your-email@gmail.com");

    mailSender.send(message);
  }

  // Method 3: Send Email with Thymeleaf Template (HTML)
  public void sendTemplateEmail(
    String to,
    String subject,
    String templateName,
    Context context
  ) throws MessagingException {
    String htmlContent = templateEngine.process(templateName, context);
    sendHtmlEmail(to, subject, htmlContent);
  }

  // Method 4: Send Email with Attachment
  public void sendEmailWithAttachment(
    String to,
    String subject,
    String text,
    String attachmentPath,
    String attachmentName
  ) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);

    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(text);
    helper.setFrom("your-email@gmail.com");

    // Add attachment
    // FileSystemResource file = new FileSystemResource(new File(attachmentPath));
    // helper.addAttachment(attachmentName, file);

    mailSender.send(message);
  }
}
