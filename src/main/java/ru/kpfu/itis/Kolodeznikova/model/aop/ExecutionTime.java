package ru.kpfu.itis.Kolodeznikova.model.aop;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class ExecutionTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String methodName;

    private long durationMs;

    private Instant timestamp;

    public ExecutionTime() {
    }

    public ExecutionTime(String methodName, long durationMs) {
        this.methodName = methodName;
        this.durationMs = durationMs;
        this.timestamp = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}