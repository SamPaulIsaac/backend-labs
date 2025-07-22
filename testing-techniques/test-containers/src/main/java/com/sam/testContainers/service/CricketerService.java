package com.sam.testContainers.service;

import com.sam.testContainers.entity.Cricketer;
import com.sam.testContainers.repository.CricketerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CricketerService {
    @Autowired
    private CricketerRepository cricketerRepository;

    public Cricketer saveCricketer(Cricketer cricketer) {
        return cricketerRepository.save(cricketer);
    }

    public List<Cricketer> getAllCricketers() {
        return cricketerRepository.findAll();
    }

    public Cricketer getCricketerById(Long id) {
        return cricketerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cricketer not found"));
    }

    public void deleteCricketer(Long id) {
        cricketerRepository.deleteById(id);
    }
}

