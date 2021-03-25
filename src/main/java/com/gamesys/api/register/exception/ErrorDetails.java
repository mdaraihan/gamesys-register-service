package com.gamesys.api.register.exception;

import java.time.LocalDateTime;

class ErrorDetails {
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;

    private ErrorDetails(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    static class Builder {
        private LocalDateTime timestamp;
        private String message;
        private String details;

        Builder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        Builder withDetails(String details) {
            this.details = details;
            return this;
        }

        ErrorDetails build() {
            return new ErrorDetails(timestamp, message, details);
        }
    }
}
