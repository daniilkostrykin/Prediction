package org.example.prediction.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.prediction.dto.DashboardDto;
import org.example.prediction.services.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public String getDashboard(Model model){
        List<DashboardDto> dashboardData = dashboardService.getLeaderboard();
        model.addAttribute("userStats", dashboardData);
        return "users/dashboard";
    }
}
