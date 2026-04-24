package ru.kpfu.itis.Kolodeznikova.model.aop;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MethodMetric {

    @Id
    private String methodName;

    private int successCount;
    private int failureCount;

    public MethodMetric() {}

    public MethodMetric(String methodName) {
        this.methodName = methodName;
        this.successCount = 0;
        this.failureCount = 0;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public void incrementSuccess() {
        this.successCount++;
    }

    public void incrementFailure() {
        this.failureCount++;
    }
}
