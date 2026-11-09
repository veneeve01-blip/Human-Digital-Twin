package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.Sleep;
import com.humandigitaltwin.backend.service.SleepService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/sleep")
public class SleepController {

    private final SleepService sleepService;

    public SleepController(SleepService sleepService) {
        this.sleepService = sleepService;
    }

    @PostMapping
    public Sleep createSleep(@RequestBody Sleep sleep) {
        return sleepService.saveSleep(sleep);
    }

    @GetMapping("/user/{userId}")
    public List<Sleep> getSleep(@PathVariable Long userId) {
        return sleepService.getSleepByUserId(userId);
    }
}