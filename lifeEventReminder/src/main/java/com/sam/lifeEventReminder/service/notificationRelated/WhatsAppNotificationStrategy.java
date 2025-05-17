package com.sam.lifeEventReminder.service.notificationRelated;

import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.utility.Channel;
import org.springframework.stereotype.Service;

@Service
public class WhatsAppNotificationStrategy implements NotificationStrategy {
    @Override
    public boolean supports(Channel channel) {
        return channel == Channel.WHATSAPP || channel == Channel.BOTH;
    }

    @Override
    public void sendNotification(Event event, String message) {
        for (String number : event.getRecipients().split(",")) {
            System.out.println("Sending notification through whatsapp to " + number.trim() + ": " + message);
        }
    }
}
