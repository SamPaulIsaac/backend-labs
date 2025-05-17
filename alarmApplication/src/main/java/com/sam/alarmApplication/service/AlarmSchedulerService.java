package com.sam.alarmApplication.service;

import com.sam.alarmApplication.entity.Alarm;
import com.sam.alarmApplication.repository.AlarmRepository;
import com.sam.alarmApplication.utility.RecurrencePattern;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;


@Service
@AllArgsConstructor
public class AlarmSchedulerService {

    private TaskScheduler taskScheduler;
    private AlarmRepository alarmRepository;
    private Map<Long, ScheduledFuture<?>> scheduledTasks;


    public void scheduleAlarm(Alarm alarm) {
        Date alarmTime = Date.from(alarm.getTime().atZone(ZoneId.systemDefault()).toInstant());
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(() -> {
            triggerAlarm(alarm);
            if (alarm.getIsRecurring()) {
                alarm.setTime(calculateNextRecurrence(alarm));
                alarmRepository.save(alarm);
                scheduleAlarm(alarm);  // Reschedule next occurrence
            }
        }, alarmTime);
        scheduledTasks.put(alarm.getId(), scheduledTask);
    }

    public void cancelScheduledAlarm(Long alarmId) {
        ScheduledFuture<?> scheduledTask = scheduledTasks.get(alarmId);
        if (scheduledTask != null && !scheduledTask.isDone()) {
            scheduledTask.cancel(false);
        }
        scheduledTasks.remove(alarmId);
    }


    private LocalDateTime calculateNextRecurrence(Alarm alarm) {
        LocalDateTime nextTime = alarm.getTime();
        RecurrencePattern pattern = alarm.getRecurrencePattern();

        return switch (pattern) {
            case DAILY -> nextTime.plusDays(1);
            case WEEKLY -> nextTime.plusWeeks(1);
            case MONTHLY -> nextTime.plusMonths(1);
        };
    }


    private void triggerAlarm(Alarm alarm) {
        String ringtoneFile = alarm.getRingtone();
        if (ringtoneFile == null || ringtoneFile.isEmpty()) {
            ringtoneFile = "alarm_notification1.wav"; // default
        }
        playWavFile(ringtoneFile);
    }


    public void playWavFile(String filename) {
        try {
            File audioFile = new File(filename);
            if (!audioFile.exists()) {
                System.err.println("Audio file not found: " + filename);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();

            // Optional: wait till the clip finishes playing
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}


