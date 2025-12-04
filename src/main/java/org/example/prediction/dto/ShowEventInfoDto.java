package org.example.prediction.dto;

import java.time.Instant;
import java.util.List;

public class ShowEventInfoDto {
    private Long id;
    private String title;
    private String status;
    private Instant closesAt;
    private List<OptionDto> topOptions;

    // Конструктор по умолчанию для ModelMapper
    public ShowEventInfoDto() {
    }

    // Основной конструктор
    public ShowEventInfoDto(Long id, String title, String status, Instant closesAt, List<OptionDto> topOptions) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.closesAt = closesAt;
        this.topOptions = topOptions != null ? topOptions : List.of();
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getClosesAt() {
        return closesAt;
    }

    public void setClosesAt(Instant closesAt) {
        this.closesAt = closesAt;
    }

    public List<OptionDto> getTopOptions() {
        return topOptions;
    }

    public void setTopOptions(List<OptionDto> topOptions) {
        this.topOptions = topOptions != null ? topOptions : List.of();
    }
}
