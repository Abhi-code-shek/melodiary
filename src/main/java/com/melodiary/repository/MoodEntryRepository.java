package com.melodiary.repository;

import com.melodiary.entity.MoodEntry;
import com.melodiary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long> {

    List<MoodEntry> findByUserOrderByLoggedDateDesc(User user);

    Optional<MoodEntry> findByUserAndLoggedDate(User user, LocalDate date);

    boolean existsByUserAndLoggedDate(User user, LocalDate date);
}