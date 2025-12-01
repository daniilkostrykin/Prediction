package org.example.bet.services;

import org.example.bet.dto.form.AddPredictionDto;

public interface PredictionService {

    void makePrediction(Long id, AddPredictionDto form);

}