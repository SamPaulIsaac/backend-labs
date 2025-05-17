package com.sam.alarmApplication.controller;

import com.sam.alarmApplication.dto.AlarmDto;
import com.sam.alarmApplication.entity.Alarm;
import com.sam.alarmApplication.globalResponseHandler.ApiSuccessDto;
import com.sam.alarmApplication.service.AlarmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alarms")
@AllArgsConstructor
@Slf4j
public class AlarmController {

    private AlarmService alarmService;

    @PostMapping
    public ResponseEntity<ApiSuccessDto<AlarmDto>> createAlarm(@RequestBody Alarm alarm) {
        log.info("Request received to create alarm.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, alarmService.createAlarm(alarm)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiSuccessDto<List<AlarmDto>>> getAllAlarms() {
        log.info("Request received to get all alarms.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, alarmService.getAllAlarms()));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiSuccessDto<List<AlarmDto>>> getAllActiveAlarms() {
        log.info("Request received to get all active alarms.");
        return ResponseEntity.ok(new ApiSuccessDto<>(200, alarmService.getAllActiveAlarms()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessDto<AlarmDto>> updateAlarm(@PathVariable Long id, @RequestBody Alarm alarm) {
        AlarmDto updated = alarmService.updateAlarm(id, alarm);
        return ResponseEntity.ok(new ApiSuccessDto<>(200, updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessDto<String>> deleteAlarm(@PathVariable Long id) {
        log.info("Request received to delete alarm by id: {}", id);
        alarmService.deleteAlarm(id);
        return ResponseEntity.ok(new ApiSuccessDto<>(200, "Deleted successfully."));
    }
}
