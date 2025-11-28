package org.example.bet.dto.admin;

public record AdminDashboardViewModel(
        long totalUsers,
        long activeEvents,
        long predictionsMadeToday
) {}