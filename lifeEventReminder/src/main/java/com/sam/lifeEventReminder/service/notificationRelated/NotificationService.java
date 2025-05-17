package com.sam.lifeEventReminder.service.notificationRelated;

import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.utility.NotificationFeature;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.togglz.core.manager.FeatureManager;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final List<NotificationStrategy> strategies;
    private final FeatureManager featureManager;


    public void sendNotifications(List<Event> events, String messagePrefix) {
        for (Event event : events) {
            String message = messagePrefix + " - " + event.getTitle() + " on " + event.getEventDate();

            for (NotificationStrategy strategy : strategies) {
                if (strategy.supports(event.getChannel()) && isFeatureEnabledForStrategy(strategy)) {
                    strategy.sendNotification(event, message);
                }
            }
        }
    }

    private boolean isFeatureEnabledForStrategy(NotificationStrategy strategy) {
        // Implement your togglz featureManager checks per strategy class
        if (strategy instanceof EmailNotificationStrategy) {
            return featureManager.isActive(NotificationFeature.EMAIL_NOTIFICATION)
                    || featureManager.isActive(NotificationFeature.BOTH_NOTIFICATION);
        } else if (strategy instanceof WhatsAppNotificationStrategy) {
            return featureManager.isActive(NotificationFeature.WHATSAPP_NOTIFICATION)
                    || featureManager.isActive(NotificationFeature.BOTH_NOTIFICATION);
        }
        return false;
    }
}
