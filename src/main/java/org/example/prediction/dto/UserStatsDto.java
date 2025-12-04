package org.example.prediction.dto;

public class UserStatsDto implements java.io.Serializable {
    private String username;
    private int successfulPredictions;
    private long totalPredictionsCount;
    private double successRate;

    // Конструктор по умолчанию для ModelMapper
    public UserStatsDto() {
    }

    // Основной конструктор
    public UserStatsDto(String username, int successfulPredictions, long totalPredictionsCount, double successRate) {
        this.username = username;
        this.successfulPredictions = successfulPredictions;
        this.totalPredictionsCount = totalPredictionsCount;
        this.successRate = successRate;
    }

    // Геттеры и сеттеры
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSuccessfulPredictions() {
        return successfulPredictions;
    }

    public void setSuccessfulPredictions(int successfulPredictions) {
        this.successfulPredictions = successfulPredictions;
    }

    public long getTotalPredictionsCount() {
        return totalPredictionsCount;
    }

    public void setTotalPredictionsCount(long totalPredictionsCount) {
        this.totalPredictionsCount = totalPredictionsCount;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }
}