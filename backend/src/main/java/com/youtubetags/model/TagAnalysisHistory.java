package com.youtubetags.model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TagAnalysisHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Instant createdOn;

    private String tagName;
    private Integer totalResults;
    private Integer returnedVideos;
    private Double tagScore;

    public TagAnalysisHistory() {
    }

    public TagAnalysisHistory(String tagName, Integer totalResults, Integer returnedVideos, Double tagScore) {
        this.tagName = tagName;
        this.totalResults = totalResults;
        this.returnedVideos = returnedVideos;
        this.tagScore = tagScore;
    }

    public TagAnalysisHistory(TagAnalysis tagAnalysis) {
        this.tagName = tagAnalysis.getTagName();
        this.totalResults = tagAnalysis.getTotalResults();
        this.returnedVideos = tagAnalysis.getReturnedVideos();
        this.tagScore = tagAnalysis.getTagScore();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getReturnedVideos() {
        return returnedVideos;
    }

    public void setReturnedVideos(Integer returnedVideos) {
        this.returnedVideos = returnedVideos;
    }

    public Double getTagScore() {
        return tagScore;
    }

    public void setTagScore(Double tagScore) {
        this.tagScore = tagScore;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

}
