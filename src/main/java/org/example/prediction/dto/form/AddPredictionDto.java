package org.example.prediction.dto.form;


public class AddPredictionDto implements java.io.Serializable {
    private Long eventId;
    private Long chosenOptionId;

    public AddPredictionDto() {
    }

    public AddPredictionDto(Long eventId, Long chosenOptionId) {
        this.eventId = eventId;
        this.chosenOptionId = chosenOptionId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getChosenOptionId() {
        return chosenOptionId;
    }

    public void setChosenOptionId(Long chosenOptionId) {
        this.chosenOptionId = chosenOptionId;
    }
}