package org.example.prediction.dto.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPredictionDto implements java.io.Serializable {
    private Long eventId;
    private Long chosenOptionId;
}