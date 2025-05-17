package com.sam.lifeEventReminder.repository;

import com.sam.lifeEventReminder.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByEventDate(LocalDate eventDate);

    @Query("SELECT e FROM Event e WHERE FUNCTION('MONTH', e.eventDate) = :month")
    List<Event> findByEventDateMonth(@Param("month") int month);
}
