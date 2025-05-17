package com.sam.lifeEventReminder.controller;


import com.sam.lifeEventReminder.dto.EventDto;
import com.sam.lifeEventReminder.entity.Event;
import com.sam.lifeEventReminder.globalResponseHandler.ApiSuccessDto;
import com.sam.lifeEventReminder.service.EventService;
import com.sam.lifeEventReminder.utility.CsvHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@AllArgsConstructor
@Slf4j
public class EventController {

    private EventService service;
    private CsvHelper csvHelper;

    /**
     * Requirements:
     * 1. POST APIs should support both single and bulk (list) event submissions.
     * 2. Bulk uploads must also be supported via CSV file input.
     */

    @PostMapping("/add")
    public ResponseEntity<ApiSuccessDto<EventDto>> addEvent(@RequestBody Event event) {
        log.info("Request received to add event.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, service.addEvent(event)));
    }

    @PostMapping("/addListOfEvents")
    public ResponseEntity<ApiSuccessDto<List<EventDto>>> addListOfEvents(@RequestBody List<Event> events) {
        log.info("Request received to add list of events.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, service.addEvents(events)));
    }

    @PostMapping("/bulk-csv")
    public ResponseEntity<ApiSuccessDto<List<EventDto>>> uploadCsvFile(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("CSV file upload request received.");
        List<Event> events = csvHelper.parse(file.getInputStream());
        return ResponseEntity.ok(new ApiSuccessDto<>(200, service.addEvents(events)));
    }

    /**
     * 1. Handles GET requests to retrieve all events.
     * 2. If a specific month is provided as a query parameter, returns only the events for that month.
     */

    @GetMapping
    public ResponseEntity<ApiSuccessDto<List<Event>>> getAllEvents() {
        log.info("Request received to get all events.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, service.getAllEvents()));
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<ApiSuccessDto<List<EventDto>>> getEventsByMonth(@PathVariable int month) {
        log.info("Request received to get event by months.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, service.getEventsByMonth(month)));
    }

    /**
     * Handles DELETE request to remove a specific event.
     * Deletes the event identified by the provided ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessDto<String>> deleteEvent(@PathVariable Long id) {
        log.info("Request received to delete event by id: {}", id);
        service.deleteEvent(id);
        return ResponseEntity.ok(new ApiSuccessDto<>(200, "Deleted successfully."));
    }
}




