package com.sam.lifeEventReminder.service;

import com.sam.lifeEventReminder.dto.EventDto;
import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.repository.EventRepository;
import com.sam.lifeEventReminder.service.notificationRelated.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class EventService {

    private EventRepository repository;
    private NotificationService notificationService;
    private ModelMapper modelMapper;


    public EventDto addEvent(Event event) {
        Event savedEvent = repository.saveAndFlush(event);
        log.info("Event saved successfully.");
        LocalDate eventDate = savedEvent.getEventDate();
        LocalDate today = LocalDate.now();

        if (eventDate.isEqual(today)) {
            notificationService.sendNotifications(List.of(savedEvent), "Manual Trigger: Today's the event!");
        } else if (eventDate.isEqual(today.plusDays(1))) {
            notificationService.sendNotifications(List.of(savedEvent), "Manual Trigger: Your event is tomorrow!");
        }

        return modelMapper.map(savedEvent, EventDto.class);
    }

    public List<EventDto> addEvents(List<Event> events) {
        List<Event> eventList = repository.saveAllAndFlush(events);
        return eventList.stream().map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());
    }


    public List<Event> getAllEvents() {
        return repository.findAll();
    }

    public List<Event> getEventsByDate(LocalDate date) {
        return repository.findByEventDate(date);
    }

    public List<EventDto> getEventsByMonth(int month) {
        List<Event> eventList = repository.findByEventDateMonth(month);
        if (eventList.isEmpty()) throw new EntityNotFoundException("Events does not exist for the " +
                "requested month: " + month);
        return eventList.stream().map(event -> modelMapper.map(event, EventDto.class))
                .collect(Collectors.toList());
    }

    public void deleteEvent(Long id) {
        repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Event does not exist for the " +
                                "requested id: " + id));
        repository.deleteById(id);
        log.info("Event deleted successfully!");
    }
}
