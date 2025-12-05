package org.example.prediction.dto;

public class OptionDto implements java.io.Serializable {
    private Long optionId;
    private String text;
    private int percentage;

    public OptionDto() {
    }

    public OptionDto(Long optionId, String text, int percentage) {
        this.optionId = optionId;
        this.text = text;
        this.percentage = percentage;
    }

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
