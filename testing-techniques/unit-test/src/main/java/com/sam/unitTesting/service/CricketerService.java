package com.sam.unitTesting.service;

import com.sam.unitTesting.entity.Cricketer;
import com.sam.unitTesting.repository.CricketerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CricketerService {

    @Autowired
    private CricketerRepository cricketerRepository;

    public Cricketer saveCricketer(Cricketer cricketer) {
        log.info("Saving cricketer: {}", cricketer);
        Cricketer saved = cricketerRepository.save(cricketer);
        log.info("Cricketer saved with ID: {}", saved.getId());
        return saved;
    }

    public List<Cricketer> getAllCricketers() {
        log.info("Fetching all cricketers from repository");
        List<Cricketer> list = cricketerRepository.findAll();
        log.info("Total cricketers fetched: {}", list.size());
        return list;
    }

    public Cricketer getCricketerById(Long id) {
        log.info("Fetching cricketer with ID: {}", id);
        return cricketerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cricketer not found for ID: {}", id);
                    return new EntityNotFoundException("Cricketer not found");
                });
    }

    public void deleteCricketer(Long id) {
        log.info("Deleting cricketer with ID: {}", id);
        cricketerRepository.deleteById(id);
        log.info("Cricketer deleted with ID: {}", id);
    }
}
