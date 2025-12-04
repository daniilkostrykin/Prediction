package org.example.prediction.dto.admin;

public class AdminDashboardViewModel {
    private long totalUsers;
    private long activeEvents;
    private long predictionsMadeToday;

    // Конструктор по умолчанию для ModelMapper
    public AdminDashboardViewModel() {
    }

    // Основной конструктор
    public AdminDashboardViewModel(long totalUsers, long activeEvents, long predictionsMadeToday) {
        this.totalUsers = totalUsers;
        this.activeEvents = activeEvents;
        this.predictionsMadeToday = predictionsMadeToday;
    }

    // Геттеры и сеттеры
    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getActiveEvents() {
        return activeEvents;
    }

    public void setActiveEvents(long activeEvents) {
        this.activeEvents = activeEvents;
    }

    public long getPredictionsMadeToday() {
        return predictionsMadeToday;
    }

    public void setPredictionsMadeToday(long predictionsMadeToday) {
        this.predictionsMadeToday = predictionsMadeToday;
    }
}