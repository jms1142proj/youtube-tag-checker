package com.youtubetags.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.youtubetags.model.TagAnalysis;
import com.youtubetags.model.TagAnalysisHistory;
import com.youtubetags.repository.TagAnalysisRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class YouTubeAnalysisService {

    @Value("${youtube.api.key}")
    private String apiKey;

    private final TagAnalysisRepository tagAnalysisRepository;

    public YouTubeAnalysisService(TagAnalysisRepository tagAnalysisRepository) {
        this.tagAnalysisRepository = tagAnalysisRepository;
    }

    public ArrayList<TagAnalysis> analyzeTags(List<String> tags) {
        ArrayList<TagAnalysis> results = new ArrayList<>();

        for (String tag : tags) {
            try {
                YouTube youtube = new YouTube.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        GsonFactory.getDefaultInstance(),
                        null).setApplicationName("TagChecker").build();

                // Search for videos
                SearchListResponse response = youtube.search()
                        .list(Arrays.asList("id", "snippet"))
                        .setQ(tag)
                        .setMaxResults(200L)
                        .setKey(this.apiKey)
                        .execute();

                int returnedVideos = response.getItems().size();
                Integer totalResults = response.getPageInfo().getTotalResults();

                // Calculate score using your formula
                double score = 100.0 / Math.log10(totalResults + 1);
                TagAnalysis tagAnalysis = new TagAnalysis(tag, totalResults, returnedVideos, score);
                results.add(tagAnalysis);
                TagAnalysisHistory tagAnalysisHistory = new TagAnalysisHistory(tagAnalysis);
                tagAnalysisRepository.save(tagAnalysisHistory);

            } catch (Exception e) {
                // TODO: handle exception
                TagAnalysis errorResult = new TagAnalysis(tag, 0, 0, 0.0);
                results.add(errorResult);
                TagAnalysisHistory tagAnalysisHistory = new TagAnalysisHistory(errorResult);
                tagAnalysisRepository.save(tagAnalysisHistory);
            }
        }
        return results;
    }
}
