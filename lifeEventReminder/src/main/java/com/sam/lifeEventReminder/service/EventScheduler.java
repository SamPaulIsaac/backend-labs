package com.sam.lifeEventReminder.service;

import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.service.notificationRelated.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class EventScheduler {

    private EventService eventService;
    private NotificationService notificationService;

    // Runs every day at 8 PM (for next-day events)
    @Scheduled(cron = "0 0 20 * * *")
    public void notifyBeforeEvent() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<Event> events = eventService.getEventsByDate(tomorrow);
        notificationService.sendNotifications(events, "Reminder: Your event is tomorrow!");
    }

    // Runs every day at 6 AM (for today's events)
    @Scheduled(cron = "0 0 6 * * *")
    public void notifyOnEventDay() {
        LocalDate today = LocalDate.now();
        List<Event> events = eventService.getEventsByDate(today);
        notificationService.sendNotifications(events, "Today’s the day! Don't forget your event!");
    }

    // Manually trigger notifications (for testing)
    @PostConstruct
    public void triggerSchedulerManually() {
        // Simulate tomorrow's date (for testing)
        LocalDate testDate = LocalDate.now().plusDays(1);
        List<Event> testEvents = eventService.getEventsByDate(testDate);
        notificationService.sendNotifications(testEvents, "Manual Trigger: Your event is tomorrow!");

        // Simulate today's date (for testing)
        LocalDate today = LocalDate.now();
        List<Event> todayEvents = eventService.getEventsByDate(today);
        notificationService.sendNotifications(todayEvents, "Manual Trigger: Today's the event!");
    }
}

