package org.example.prediction.dto;

public class OptionDto implements java.io.Serializable {
    private Long optionId;
    private String text;
    private int percentage;

    // Конструктор по умолчанию для ModelMapper
    public OptionDto() {
    }

    // Основной конструктор
    public OptionDto(Long optionId, String text, int percentage) {
        this.optionId = optionId;
        this.text = text;
        this.percentage = percentage;
    }

    // Геттеры и сеттеры
    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
