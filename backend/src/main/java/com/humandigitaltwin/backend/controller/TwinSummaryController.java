package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.*;
import com.humandigitaltwin.backend.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/twin")
public class TwinSummaryController {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ActivityRepository activityRepository;
    private final HabitRepository habitRepository;
    private final MoodRepository moodRepository;
    private final SleepRepository sleepRepository;

    public TwinSummaryController(
            UserRepository userRepository,
            ProfileRepository profileRepository,
            ActivityRepository activityRepository,
            HabitRepository habitRepository,
            MoodRepository moodRepository,
            SleepRepository sleepRepository) {

        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.activityRepository = activityRepository;
        this.habitRepository = habitRepository;
        this.moodRepository = moodRepository;
        this.sleepRepository = sleepRepository;
    }

    @GetMapping("/{userId}")
    public Map<String, Object> getTwinSummary(@PathVariable Long userId) {

        Map<String, Object> summary = new LinkedHashMap<>();

        summary.put("user", userRepository.findById(userId).orElse(null));
        summary.put("profile", profileRepository.findByUserId(userId).orElse(null));
        summary.put("activities", activityRepository.findByUserId(userId));
        summary.put("habits", habitRepository.findByUserId(userId));
        summary.put("moods", moodRepository.findByUserId(userId));
        summary.put("sleep", sleepRepository.findByUserId(userId));

        return summary;
    }
}