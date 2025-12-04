package org.example.prediction.dto.form;


public class AddPredictionDto {
    private Long eventId;
    private Long chosenOptionId;

    // Конструктор по умолчанию для ModelMapper
    public AddPredictionDto() {
    }

    // Основной конструктор
    public AddPredictionDto(Long eventId, Long chosenOptionId) {
        this.eventId = eventId;
        this.chosenOptionId = chosenOptionId;
    }

    // Геттеры и сеттеры
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