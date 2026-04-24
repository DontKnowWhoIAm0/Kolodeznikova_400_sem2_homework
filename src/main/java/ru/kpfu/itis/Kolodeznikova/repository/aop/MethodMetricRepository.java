package ru.kpfu.itis.Kolodeznikova.repository.aop;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.Kolodeznikova.model.aop.MethodMetric;

public interface MethodMetricRepository extends JpaRepository<MethodMetric, String> {
}
