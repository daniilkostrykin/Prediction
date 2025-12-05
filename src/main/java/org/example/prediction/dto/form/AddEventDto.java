package org.example.prediction.dto.form;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AddEventDto implements java.io.Serializable {
    @NotBlank(message = "Название события не может быть пустым")
    @Size(min = 5, max = 100, message = "Название должно быть от 5 до 100 символов")
    private String title;

    @Size(max = 50, message = "Описание должно быть до 500 символов")
    private String description;

    @NotEmpty(message = "Варианты выбора должны быть")
    @Size(min = 2, message = "Не менее 2-х вариантов выбора")
    private List<String> options;

    @NotNull(message = "Укажите дату и время окончания события")
    @Future(message = "Дата и время окончания должны быть в будущем")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime closesAt;

    public AddEventDto() {}

    public AddEventDto(String title, String description, List<String> options, LocalDateTime closesAt) {
        this.title = title;
        this.description = description;
        this.options = options;
        this.closesAt = closesAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public LocalDateTime getClosesAt() { return closesAt; }
    public void setClosesAt(LocalDateTime closesAt) { this.closesAt = closesAt; }
}