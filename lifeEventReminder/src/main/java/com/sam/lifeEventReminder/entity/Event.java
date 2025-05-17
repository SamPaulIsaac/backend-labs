package com.sam.lifeEventReminder.entity;

import com.sam.lifeEventReminder.utility.Channel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "event_date")
    private LocalDate eventDate;

    private String recipients; // comma-separated emails or numbers

    @Enumerated(EnumType.STRING)
    private Channel channel; // EMAIL, WHATSAPP, BOTH

    private String createdBy;

    private String updatedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

