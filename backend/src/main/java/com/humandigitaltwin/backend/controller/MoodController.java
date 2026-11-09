package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.Mood;
import com.humandigitaltwin.backend.service.MoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/moods")
public class MoodController {

    private final MoodService moodService;

    public MoodController(MoodService moodService) {
        this.moodService = moodService;
    }

    @PostMapping
    public Mood createMood(@RequestBody Mood mood) {
        return moodService.saveMood(mood);
    }

    @GetMapping("/user/{userId}")
    public List<Mood> getMoods(@PathVariable Long userId) {
        return moodService.getMoodsByUserId(userId);
    }
}