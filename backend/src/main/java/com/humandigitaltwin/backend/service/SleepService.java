package com.humandigitaltwin.backend.service;

import com.humandigitaltwin.backend.entity.Sleep;
import com.humandigitaltwin.backend.repository.SleepRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SleepService {

    private final SleepRepository sleepRepository;

    public SleepService(SleepRepository sleepRepository) {
        this.sleepRepository = sleepRepository;
    }

    public Sleep saveSleep(Sleep sleep) {
        return sleepRepository.save(sleep);
    }

    public List<Sleep> getSleepByUserId(Long userId) {
        return sleepRepository.findByUserId(userId);
    }
}