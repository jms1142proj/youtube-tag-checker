package com.youtubetags.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.youtubetags.model.TagAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class YouTubeAnalysisService {

    @Value("${youtube.api.key}")
    private String apiKey;

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

            } catch (Exception e) {
                // TODO: handle exception
                TagAnalysis errorResult = new TagAnalysis(tag, 0, 0, 0.0);
                results.add(errorResult);
            }
        }
        return results;
    }
}
