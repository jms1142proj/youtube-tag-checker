package com.youtubetags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Arrays;

public class YoutubeTagChecker {
    private String apiKey;

    public YoutubeTagChecker(String apiKey) { // Constructor
        this.apiKey = apiKey;
    }

    public String getKey() {
        return this.apiKey;
    }

    public void setKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public static void main(String[] args) {

        if (args.length > 0) {
            YoutubeTagChecker checker = new YoutubeTagChecker(args[0]); // Create object
            if (checker.isValidApiKey()) {
                ArrayList<String> tags = checker.getUserTags();
                HashMap<String, Double> tagMap = new HashMap<>();
                for (String tag : tags) {
                    tagMap.put(tag, checker.analyzeTag(tag));
                }
                for (Map.Entry<String, Double> entry : tagMap.entrySet()) {
                    System.out.println("Tag: " + entry.getKey() + ", Score: " + entry.getValue());
                }
            } else {
                System.out.println("invalid key");
            }
        } else {
            System.out.println("No argument");
        }
    }

    private boolean isValidApiKey() {
        return this.apiKey != null && !this.apiKey.isEmpty();
    }

    private ArrayList<String> getUserTags() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tags = new ArrayList<>();
        String[] scanned = scanner.nextLine().split(",");
        for (String tag : scanned) {
            tags.add(tag.trim());
        }
        return tags;
    }

    public double analyzeTag(String tag) {
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

            int videoCount = response.getItems().size();
            Integer totalResults = response.getPageInfo().getTotalResults();
            System.out.println(
                    "API returned " + videoCount + " videos out of " + totalResults + " total available for: " + tag);
            if (videoCount == 0) {
                System.out.println("No videos found");
                return 0.0;
            }
            return 100.0 / Math.log10(totalResults + 1);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0.0;
        }
    }
}
