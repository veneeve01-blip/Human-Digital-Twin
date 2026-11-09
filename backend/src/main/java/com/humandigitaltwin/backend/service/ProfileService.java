package com.humandigitaltwin.backend.service;

import com.humandigitaltwin.backend.entity.Profile;
import com.humandigitaltwin.backend.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile saveProfile(Profile profile) {

        if (profile.getUser() == null ||
            profile.getUser().getId() == null) {

            throw new RuntimeException(
                    "User is required"
            );
        }

        Optional<Profile> existingProfile =
                profileRepository.findByUserId(
                        profile.getUser().getId()
                );

        if (existingProfile.isPresent()) {

            Profile existing =
                    existingProfile.get();

            existing.setAge(profile.getAge());
            existing.setOccupation(
                    profile.getOccupation()
            );
            existing.setLocation(
                    profile.getLocation()
            );
            existing.setHobbies(
                    profile.getHobbies()
            );
            existing.setGoals(
                    profile.getGoals()
            );
            existing.setInterests(
                    profile.getInterests()
            );
            existing.setAbout(
                    profile.getAbout()
            );

            return profileRepository.save(
                    existing
            );
        }

        return profileRepository.save(profile);
    }

    public Optional<Profile> getProfileByUserId(
            Long userId) {

        return profileRepository.findByUserId(
                userId
        );
    }
}