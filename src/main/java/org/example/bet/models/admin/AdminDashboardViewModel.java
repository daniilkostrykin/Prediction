package org.example.bet.models.admin;

import java.math.BigDecimal;

public record AdminDashboardViewModel(
        long totalUsers,
        long activeEvents,
        BigDecimal totalBetsAmountToday
) {}