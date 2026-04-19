package com.melodiary.controller;

import com.melodiary.dto.YouTubeSearchResponse;
import com.melodiary.service.YouTubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/youtube")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class YouTubeController {

    private final YouTubeService youTubeService;

    @GetMapping("/search")
    public ResponseEntity<YouTubeSearchResponse> search(
            @RequestParam String query) {
        return ResponseEntity.ok(youTubeService.search(query));
    }
}