package com.upform.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class Recommendation {
    private Long id;
    private Long authId;
    private LocalDate date;
    private String suggestion;
    private String reason;

    public Recommendation() {}

    public Recommendation (
            Long id,
            Long authId,
            LocalDate date,
            String suggestion,
            String reason
    ) {
        this.id = id;
        this.authId = authId;
        this.date = date;
        this.suggestion = suggestion;
        this.reason = reason;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAuthId() { return authId; }
    public void setAuthId(Long authId) { this.authId = authId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
