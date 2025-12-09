package org.example.prediction.web;
import org.springframework.security.access.prepost.PreAuthorize;

import org.example.prediction.dto.admin.AdminDashboardViewModel;
import org.example.prediction.models.entities.Event;
import org.example.prediction.models.entities.User;
import org.example.prediction.services.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model) {
        // Получаем всех пользователей
        List<User> allUsers = adminService.getAllUsers();
        
        // Получаем события, ожидающие завершения (closesAt прошло, но статус еще ACTIVE)
        List<Event> pendingEvents = adminService.getPendingEvents();
        
        // Получаем статистику для AdminDashboardViewModel
        AdminDashboardViewModel dashboardStats = adminService.getDashboardStats();
        
        model.addAttribute("users", allUsers);
        model.addAttribute("pendingEvents", pendingEvents);
        model.addAttribute("dashboardStats", dashboardStats);
        
        return "admin";
    }
}