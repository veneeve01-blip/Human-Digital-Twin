package com.humandigitaltwin.backend.repository;

import com.humandigitaltwin.backend.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoodRepository extends JpaRepository<Mood, Long> {

    List<Mood> findByUserId(Long userId);

}