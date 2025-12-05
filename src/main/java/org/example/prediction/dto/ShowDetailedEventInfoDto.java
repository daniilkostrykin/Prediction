package org.example.prediction.dto;

import java.time.Instant;
import java.util.List;


public class ShowDetailedEventInfoDto implements java.io.Serializable {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Instant closesAt;
    private List<OptionDto> optionsWithStats;

    public ShowDetailedEventInfoDto() {
    }

    public ShowDetailedEventInfoDto(Long id, String title, String description, String status, Instant closesAt, List<OptionDto> optionsWithStats) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.closesAt = closesAt;
        this.optionsWithStats = optionsWithStats != null ? optionsWithStats : List.of();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<OptionDto> getOptionsWithStats() {
        return optionsWithStats;
    }

    public void setOptionsWithStats(List<OptionDto> optionsWithStats) {
        this.optionsWithStats = optionsWithStats != null ? optionsWithStats : List.of();
    }
}
