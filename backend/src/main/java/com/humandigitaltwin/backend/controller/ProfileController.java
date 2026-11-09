package com.humandigitaltwin.backend.controller;

import com.humandigitaltwin.backend.entity.Profile;
import com.humandigitaltwin.backend.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public Profile saveProfile(
            @RequestBody Profile profile) {

        return profileService.saveProfile(profile);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfile(
            @PathVariable Long userId) {

        return profileService
                .getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }
}