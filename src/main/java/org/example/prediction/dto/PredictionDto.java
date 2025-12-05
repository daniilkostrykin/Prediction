package org.example.prediction.dto;

import java.time.Instant;

public class PredictionDto implements java.io.Serializable {
    private String eventTitle;
    private String prediction;
    private String status;
    private Instant createdAt;

    public PredictionDto() {
    }

    public PredictionDto(String eventTitle, String prediction, String status, Instant createdAt) {
        this.eventTitle = eventTitle;
        this.prediction = prediction;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
