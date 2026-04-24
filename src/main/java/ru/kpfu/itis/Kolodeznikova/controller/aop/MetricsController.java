package ru.kpfu.itis.Kolodeznikova.controller.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Kolodeznikova.model.aop.MethodMetric;
import ru.kpfu.itis.Kolodeznikova.repository.aop.MethodMetricRepository;

import java.util.List;

@RestController
public class MetricsController {

    private final MethodMetricRepository repository;

    public MetricsController(MethodMetricRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/metrics")
    public List<MethodMetric> getMetrics() {
        return repository.findAll();
    }
}
