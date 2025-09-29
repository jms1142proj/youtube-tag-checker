package com.youtubetags.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

import com.youtubetags.model.TagAnalysis;
import com.youtubetags.model.TagAnalysisHistory;
import com.youtubetags.service.YouTubeAnalysisService;

@RestController
@RequestMapping("/api/youtube")
public class YouTubeController {

    @Autowired
    private YouTubeAnalysisService youtubeAnalysisService;

    public static class TagAnalysisRequest {
        private List<String> tags;

        public List<String> getTags() {
            return this.tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }

    @PostMapping("/analyze")
    public ResponseEntity<List<TagAnalysis>> analyzeTags(@RequestBody TagAnalysisRequest request) {
        try {
            List<TagAnalysis> results = youtubeAnalysisService.analyzeTags(request.getTags());
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/history")
    public List<TagAnalysisHistory> getAnalysisHistory() {
        return youtubeAnalysisService.getAllHistory();
    }
}