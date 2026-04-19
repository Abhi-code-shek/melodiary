package com.melodiary.service;

import com.melodiary.config.YouTubeConfig;
import com.melodiary.dto.YouTubeSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class YouTubeService {

    private final RestTemplate restTemplate;
    private final YouTubeConfig youTubeConfig;

    private static final String YOUTUBE_SEARCH_URL =
            "https://www.googleapis.com/youtube/v3/search";

    public YouTubeSearchResponse search(String query) {
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(YOUTUBE_SEARCH_URL)
                    .queryParam("part", "snippet")
                    .queryParam("q", query)
                    .queryParam("type", "video")
                    .queryParam("videoCategoryId", "10") // music category
                    .queryParam("maxResults", "8")
                    .queryParam("key", youTubeConfig.getApiKey())
                    .toUriString();

            Map<String, Object> response =
                    restTemplate.getForObject(url, Map.class);

            List<YouTubeSearchResponse.YouTubeVideoDto> videos =
                    new ArrayList<>();

            if (response != null && response.containsKey("items")) {
                List<Map<String, Object>> items =
                        (List<Map<String, Object>>) response.get("items");

                for (Map<String, Object> item : items) {
                    Map<String, Object> id =
                            (Map<String, Object>) item.get("id");
                    Map<String, Object> snippet =
                            (Map<String, Object>) item.get("snippet");
                    Map<String, Object> thumbnails =
                            (Map<String, Object>) snippet.get("thumbnails");
                    Map<String, Object> mediumThumb =
                            (Map<String, Object>) thumbnails.get("medium");

                    String videoId = (String) id.get("videoId");
                    if (videoId == null) continue;

                    videos.add(YouTubeSearchResponse.YouTubeVideoDto.builder()
                            .videoId(videoId)
                            .title((String) snippet.get("title"))
                            .channelTitle((String) snippet.get("channelTitle"))
                            .thumbnailUrl((String) mediumThumb.get("url"))
                            .build());
                }
            }

            return YouTubeSearchResponse.builder()
                    .videos(videos)
                    .build();

        } catch (Exception e) {
            log.error("YouTube search failed for query: {}", query, e);
            return YouTubeSearchResponse.builder()
                    .videos(new ArrayList<>())
                    .build();
        }
    }
}