package org.example.weather.session;

import java.time.LocalDateTime;

public class Session {
    private String sessionId;
    private int userId;
    private LocalDateTime createdAt;

    public Session(String sessionId, int userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public String getSessionId() {
        return sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
