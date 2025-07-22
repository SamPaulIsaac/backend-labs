package com.sam.lifeEventReminder.service.notificationRelated;

import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.utility.Channel;

public interface NotificationStrategy {
    boolean supports(Channel channel);

    void sendNotification(Event event, String message);
}
