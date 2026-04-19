package com.melodiary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "songs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_id", nullable = false, unique = true)
    private String videoId;

    @Column(nullable = false)
    private String title;

    @Column(name = "channel_title")
    private String channelTitle;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column
    private String duration;
}