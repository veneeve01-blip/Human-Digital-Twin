package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.Habit;
import com.humandigitaltwin.backend.service.HabitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public Habit createHabit(@RequestBody Habit habit) {
        return habitService.saveHabit(habit);
    }

    @GetMapping("/user/{userId}")
    public List<Habit> getHabits(@PathVariable Long userId) {
        return habitService.getHabitsByUserId(userId);
    }
}