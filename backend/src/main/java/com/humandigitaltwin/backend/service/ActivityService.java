package com.humandigitaltwin.backend.service;

import com.humandigitaltwin.backend.entity.Activity;
import com.humandigitaltwin.backend.repository.ActivityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public List<Activity> getActivitiesByUserId(Long userId) {
        return activityRepository.findByUserId(userId);
    }
}