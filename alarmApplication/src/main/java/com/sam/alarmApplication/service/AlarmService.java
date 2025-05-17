package com.sam.alarmApplication.service;


import com.sam.alarmApplication.dto.AlarmDto;
import com.sam.alarmApplication.entity.Alarm;
import com.sam.alarmApplication.repository.AlarmRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AlarmService {

    private AlarmSchedulerService alarmSchedulerService;
    private AlarmRepository alarmRepository;
    private ModelMapper modelMapper;


    public AlarmDto createAlarm(Alarm alarm) {
        Alarm savedAlarm = alarmRepository.save(alarm);
        if (savedAlarm.getIsActive()) alarmSchedulerService.scheduleAlarm(savedAlarm);
        return modelMapper.map(savedAlarm, AlarmDto.class);
    }

    public AlarmDto updateAlarm(Long id, Alarm updatedAlarm) {
        return alarmRepository.findById(id)
                .map(existingAlarm -> {
                    existingAlarm.setTitle(updatedAlarm.getTitle());
                    existingAlarm.setTime(updatedAlarm.getTime());
                    existingAlarm.setIsRecurring(updatedAlarm.getIsRecurring());
                    existingAlarm.setIsActive(updatedAlarm.getIsActive());
                    existingAlarm.setRecurrencePattern(updatedAlarm.getRecurrencePattern());
                    existingAlarm.setRingtone(updatedAlarm.getRingtone());
                    existingAlarm.setUpdatedBy(updatedAlarm.getUpdatedBy());
                    Alarm saved = alarmRepository.save(existingAlarm);

                    alarmSchedulerService.cancelScheduledAlarm(saved.getId());
                    if (saved.getIsActive()) {
                        alarmSchedulerService.scheduleAlarm(saved);
                    }

                    return modelMapper.map(saved, AlarmDto.class);
                })
                .orElseThrow(() -> new RuntimeException("Alarm not found with id " + id));
    }

    public List<AlarmDto> getAllAlarms() {
        return alarmRepository.findAll().stream().map(alarm -> modelMapper.map(alarm, AlarmDto.class))
                .collect(Collectors.toList());
    }

    public List<AlarmDto> getAllActiveAlarms() {
        return alarmRepository.findByIsActiveTrue().stream().map(alarm -> modelMapper.map(alarm, AlarmDto.class))
                .collect(Collectors.toList());
    }

    public void deleteAlarm(Long id) {
        alarmSchedulerService.cancelScheduledAlarm(id);
        alarmRepository.deleteById(id);
    }

}
