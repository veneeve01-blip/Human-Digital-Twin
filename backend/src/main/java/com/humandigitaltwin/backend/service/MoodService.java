package com.humandigitaltwin.backend.service;

import com.humandigitaltwin.backend.entity.Mood;
import com.humandigitaltwin.backend.repository.MoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodService {

    private final MoodRepository moodRepository;

    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    public Mood saveMood(Mood mood) {
        return moodRepository.save(mood);
    }

    public List<Mood> getMoodsByUserId(Long userId) {
        return moodRepository.findByUserId(userId);
    }
}