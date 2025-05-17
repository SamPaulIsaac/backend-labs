package com.sam.lifeEventReminder.service.notificationRelated;

import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.utility.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationStrategy implements NotificationStrategy {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean supports(Channel channel) {
        return channel == Channel.EMAIL || channel == Channel.BOTH;
    }

    @Override
    public void sendNotification(Event event, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(event.getRecipients());
        mailMessage.setSubject("Event Reminder");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}


