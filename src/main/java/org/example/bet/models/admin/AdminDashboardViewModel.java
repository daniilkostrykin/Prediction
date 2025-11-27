package org.example.bet.models.admin;

public record AdminDashboardViewModel(
        long totalUsers,
        long activeEvents,
        long predictionsMadeToday
) {}