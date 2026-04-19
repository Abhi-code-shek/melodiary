package com.melodiary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mood_entries")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoodEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "song_id")
    private Song song;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Emotion emotion;

    @Column(name = "energy_level", nullable = false)
    private Integer energyLevel;

    @ElementCollection
    @CollectionTable(
            name = "mood_entry_contexts",
            joinColumns = @JoinColumn(name = "mood_entry_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "context")
    private List<Context> contexts;

    @Column(name = "journal_text", length = 500)
    private String journalText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    @Column(name = "logged_date", nullable = false)
    private LocalDate loggedDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (loggedDate == null) {
            loggedDate = LocalDate.now();
        }
        if (visibility == null) {
            visibility = Visibility.PUBLIC;
        }
    }

    // Enums
    public enum Emotion {
        HAPPY, SAD, ANXIOUS, CALM, ENERGETIC, ANGRY, NOSTALGIC, NUMB
    }

    public enum Context {
        WORK, PERSONAL, RELATIONSHIP, HEALTH, CREATIVE, SOCIAL
    }

    public enum Visibility {
        PUBLIC, FRIENDS, PRIVATE
    }
}