package com.melodiary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YouTubeSearchResponse {

    private List<YouTubeVideoDto> videos;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class YouTubeVideoDto {
        private String videoId;
        private String title;
        private String channelTitle;
        private String thumbnailUrl;
    }
}