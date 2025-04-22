package com.zapcom.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/** Created by Rama Gopal Project Name - auth-service */
@Service
@Slf4j
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendEmail(String to, String subject, String body) {
    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(body, true); // true = enable HTML

      mailSender.send(message);
      log.info("Activation email sent to: " + to);
    } catch (MailAuthenticationException e) {
      log.error("Authentication Failed: " + e.getMessage());
      throw new RuntimeException("Mail authentication failed. Check your SMTP credentials.", e);

    } catch (Exception e) {
      log.error("Unexpected error: " + e.getMessage());
      throw new RuntimeException("Unexpected error while sending email.", e);
    }
  }
}
