package com.sltc.dait.library.service;

import com.sltc.dait.library.model.Mail;
import com.sltc.dait.library.repository.BookRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

import static java.lang.Thread.sleep;

@Service
@Slf4j
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;

    @Override
    @Async("mailSendTaskExecutor")
    public void sendEmail(Mail mail){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            mimeMessage.setFrom(new InternetAddress(mail.getFrom()));
            mimeMessage.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mail.getTo()));
            mimeMessage.setSubject(mail.getSubject());
            mimeMessage.setContent(mail.getBody(), "text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(mimeMessage);
    }

}
