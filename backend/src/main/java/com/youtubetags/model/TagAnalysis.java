package com.youtubetags.model;

public class TagAnalysis {
    private String tagName;
    private int totalResults;
    private int returnedVideos;
    private double tagScore;

    public String getTagName() {
        return tagName;
    }

    public int getReturnedVideos() {
        return returnedVideos;
    }

    public double getTagScore() {
        return tagScore;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setReturnedVideos(int returnedVideos) {
        this.returnedVideos = returnedVideos;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTagScore(double tagScore) {
        this.tagScore = tagScore;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public TagAnalysis() {
        this.tagName = null;
        this.totalResults = 0;
        this.returnedVideos = 0;
        this.tagScore = 0;
    }

    public TagAnalysis(String tagName, int totalResults, int returnedVideos, double tagScore) {
        this.tagName = tagName;
        this.totalResults = totalResults;
        this.returnedVideos = returnedVideos;
        this.tagScore = tagScore;
    }
}
