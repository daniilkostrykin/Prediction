package org.example.prediction.services;

import lombok.RequiredArgsConstructor;
import org.example.prediction.dto.DashboardDto;
import org.example.prediction.models.entities.User;
import org.example.prediction.repositories.PredictionRepository;
import org.example.prediction.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final PredictionRepository predictionRepository;


    public DashboardDto generateDashboardData(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден "));

        int wonPredictions = user.getSuccessfulPredictions();
        int totalPredictions = predictionRepository.countByUser(user);

        double winRate = 0.0;
        if (totalPredictions > 0) {
            winRate = ((double) wonPredictions / totalPredictions) * 100;
        }
        return new DashboardDto(username, totalPredictions, wonPredictions, winRate);
    }

    public List<DashboardDto> getLeaderboard() {
        List<DashboardDto> result = new ArrayList<>();
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            String username = user.getUsername();
            int wonPredictions = user.getSuccessfulPredictions();
            int totalPredictions = predictionRepository.countByUser(user);

            double winRate = 0.0;
            if (totalPredictions > 0) {
                winRate = ((double) wonPredictions / totalPredictions) * 100;
            }
            DashboardDto dto = new DashboardDto(username, totalPredictions, wonPredictions, winRate);
            result.add(dto);
        }

        return result;

    }
}
