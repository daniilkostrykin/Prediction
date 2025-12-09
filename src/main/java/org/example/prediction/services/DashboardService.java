package org.example.prediction.services;

import lombok.RequiredArgsConstructor;
import org.example.prediction.dto.DashboardDto;
import org.example.prediction.models.entities.User;
import org.example.prediction.repositories.PredictionRepository;
import org.example.prediction.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;
    private final PredictionRepository predictionRepository;

    public Page<DashboardDto> getLeaderboard(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("successfulPredictions").descending());

        Page<User> usersPage = userRepository.findAllByUsernameContainingIgnoreCase(search, pageable);

        return usersPage.map(user -> {
            String username = user.getUsername();
            int wonPredictions = user.getSuccessfulPredictions();
            int totalPredictions = predictionRepository.countByUser(user);
            double winRate = 0.0;
            if (totalPredictions > 0) {
                winRate = ((double) wonPredictions / totalPredictions) * 100;
            }
            return new DashboardDto(username, totalPredictions, wonPredictions, winRate);
        });
    }
}
