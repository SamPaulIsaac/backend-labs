package com.sam.unitTesting.controller;

import com.sam.unitTesting.entity.Cricketer;
import com.sam.unitTesting.service.CricketerService;
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
        log.info("POST /api/cricketers - Creating cricketer: {}", cricketer);
        Cricketer saved = cricketerService.saveCricketer(cricketer);
        log.info("Cricketer created with ID: {}", saved.getId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Cricketer> getAllCricketers() {
        log.info("GET /api/cricketers - Fetching all cricketers");
        List<Cricketer> cricketers = cricketerService.getAllCricketers();
        log.info("Total cricketers retrieved: {}", cricketers.size());
        return cricketers;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cricketer> getCricketerById(@PathVariable Long id) {
        log.info("GET /api/cricketers/{} - Fetching cricketer", id);
        Cricketer cricketer = cricketerService.getCricketerById(id);
        log.info("Fetched cricketer: {}", cricketer);
        return new ResponseEntity<>(cricketer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCricketer(@PathVariable Long id) {
        log.info("DELETE /api/cricketers/{} - Deleting cricketer", id);
        cricketerService.deleteCricketer(id);
        log.info("Cricketer with ID {} deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
