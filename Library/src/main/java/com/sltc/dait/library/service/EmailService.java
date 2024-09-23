package com.sltc.dait.library.service;

import com.sltc.dait.library.model.Mail;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    void sendEmail(Mail mail);
}
