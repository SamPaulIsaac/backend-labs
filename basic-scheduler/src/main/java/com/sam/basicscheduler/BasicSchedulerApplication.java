package com.sam.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableScheduling
public class BasicSchedulerApplication {

    private static final Logger log = LoggerFactory.getLogger(BasicSchedulerApplication.class);

    // Simulated in-memory session store: sessionId -> lastAccessTime
    private static final Map<String, LocalDateTime> sessions = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        preloadSessions();
        SpringApplication.run(BasicSchedulerApplication.class, args);
    }

    private static void preloadSessions() {
        sessions.put("session A", LocalDateTime.now().minusMinutes(5));  // expired
        sessions.put("session B", LocalDateTime.now());                  // active
        sessions.put("session C", LocalDateTime.now().minusMinutes(2));  // borderline
    }

    // Runs every 5 seconds to simulate session refresh
    @Scheduled(fixedRate = 5000)
    public void refreshActiveSessions() {
        String newSession = UUID.randomUUID().toString().substring(0, 8);
        sessions.put(newSession, LocalDateTime.now());
        log.info("[fixedRate] Refreshed or added session {} at {}", newSession, LocalDateTime.now());
    }

    // Runs 6 seconds after previous execution completes to clean up expired sessions
    @Scheduled(fixedDelay = 6000)
    public void cleanupExpiredSessions() {
        log.info("[fixedDelay] Session cleanup started at {}", LocalDateTime.now());

        sessions.entrySet().removeIf(entry -> {
            boolean expired = entry.getValue().isBefore(LocalDateTime.now().minusMinutes(3));
            if (expired) {
                log.info("Session {} expired and removed", entry.getKey());
            }
            return expired;
        });

        log.info("Remaining active sessions: {}", sessions.keySet());
    }

    // Runs every minute at the 20-second mark to simulate archiving
    @Scheduled(cron = "20 * * * * *")
    public void archiveSessions() {
        log.info("[cron] Archiving snapshot at {}: {} sessions active",
                LocalDateTime.now(), sessions.size());
    }
}


