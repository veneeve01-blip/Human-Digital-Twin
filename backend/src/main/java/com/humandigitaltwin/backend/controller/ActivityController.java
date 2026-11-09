package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.Activity;
import com.humandigitaltwin.backend.service.ActivityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public Activity createActivity(@RequestBody Activity activity) {
        return activityService.saveActivity(activity);
    }

    @GetMapping("/user/{userId}")
    public List<Activity> getActivities(@PathVariable Long userId) {
        return activityService.getActivitiesByUserId(userId);
    }
}