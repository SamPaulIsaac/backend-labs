package com.sam.integrationTesting.controller;

import com.sam.integrationTesting.entity.Cricketer;
import com.sam.integrationTesting.service.CricketerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cricketers")
public class CricketerController {
    @Autowired
    private CricketerService cricketerService;

    @PostMapping
    public ResponseEntity<Cricketer> createCricketer(@RequestBody Cricketer cricketer) {
        return new ResponseEntity<>(cricketerService.saveCricketer(cricketer), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Cricketer> getAllCricketers() {
        return cricketerService.getAllCricketers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cricketer> getCricketerById(@PathVariable Long id) {
        return new ResponseEntity<>(cricketerService.getCricketerById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCricketer(@PathVariable Long id) {
        cricketerService.deleteCricketer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

