package com.sam.integrationTesting.controller;

import com.sam.integrationTesting.entity.Cricketer;
import com.sam.integrationTesting.service.CricketerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cricketers")
public class CricketerController {

    @Autowired
    private CricketerService cricketerService;

    @PostMapping
    public ResponseEntity<Cricketer> createCricketer(@RequestBody Cricketer cricketer) {
        log.info("Creating cricketer: {}", cricketer);
        Cricketer saved = cricketerService.saveCricketer(cricketer);
        log.info("Cricketer created with ID: {}", saved.getId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Cricketer> getAllCricketers() {
        log.info("Fetching all cricketers");
        List<Cricketer> list = cricketerService.getAllCricketers();
        log.info("Fetched {} cricketers", list.size());
        return list;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cricketer> getCricketerById(@PathVariable Long id) {
        log.info("Fetching cricketer with ID: {}", id);
        Cricketer cricketer = cricketerService.getCricketerById(id);
        log.info("Fetched cricketer: {}", cricketer);
        return new ResponseEntity<>(cricketer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCricketer(@PathVariable Long id) {
        log.info("Deleting cricketer with ID: {}", id);
        cricketerService.deleteCricketer(id);
        log.info("Cricketer with ID {} deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
