package com.sltc.dait.library.service;

import com.sltc.dait.library.model.Lend;
import com.sltc.dait.library.model.Mail;
import com.sltc.dait.library.repository.LendRepo;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
//@RequiredArgsConstructor
@Service
public class LendServiceImpl implements LendService {
    private final LendRepo lendRepo;
    private final EmailService emailService;
    private final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Value("${dait.borrow.notification.email.from}")
    private String fromEmail;
    @Value("${dait.borrow.notification.email.subject}")
    private String emailSubject;

    @Value("${dait.borrow.notification.email.template.path}")
    private Resource lendResource;
    @Value("${dait.overdue.notification.email.template.path}")
    private Resource lendOverdueResource;

    public LendServiceImpl(LendRepo lendRepo, EmailService emailService){
        this.lendRepo = lendRepo;
        this.emailService = emailService;
    }

    @Override
    public void addLend(Lend lend){
        saveLend(lend);
        sendEmailNotification(lend, lendResource);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void saveLend(Lend lend) {
        lendRepo.save(lend);
    }

    @Override
    public List<Lend> getOverdueLendings(Date byDate) {
        return lendRepo.findOverdue(byDate);
    }


    @Scheduled(cron = "${dait.borrow.overdue.reminder.cron}")
    @SchedulerLock(name = "overdueLendNotificationScheduledJobLock") // Provide a unique lock name
    @Transactional(propagation = Propagation.REQUIRED)
    public void overdueLendNotification() {
        log.info("Start sending overdue notification emails");
        List<Lend> overdueLendings = lendRepo.findOverdue(new Date(),0);
        log.info(overdueLendings.stream().count() + " items found overdue");
        overdueLendings.forEach(lend -> {
            sendEmailNotification(lend, lendOverdueResource);
            lendRepo.increaseNotifyCount(lend.getId());
        });
    }

    private void sendEmailNotification(Lend lend, Resource resource) {
        Mail mail = Mail.builder()
                .to(lend.getLenderEmail())
                .from(fromEmail)
                .subject(emailSubject)
                .body(getEmailContent(lend,resource))
                .build();
        emailService.sendEmail(mail);
    }

    private String getEmailContent(Lend lend, Resource resource) {
        String content;
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            content = FileCopyUtils.copyToString(reader);
            return content.replace("${lendId}",lend.getId().toString())
                    .replace("${isbn}", lend.getIsbn())
                    .replace("${borrowedDate}", formatter.format(lend.getLendDate()))
                    .replace("${dueDate}", formatter.format(lend.getDueDate()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
