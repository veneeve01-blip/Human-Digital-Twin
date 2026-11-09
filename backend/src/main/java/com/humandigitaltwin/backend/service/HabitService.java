package com.humandigitaltwin.backend.service;

import com.humandigitaltwin.backend.entity.Habit;
import com.humandigitaltwin.backend.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    private final HabitRepository habitRepository;

    public HabitService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    public Habit saveHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public List<Habit> getHabitsByUserId(Long userId) {
        return habitRepository.findByUserId(userId);
    }
}