package ru.kpfu.itis.Kolodeznikova.aop.metrics;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.Kolodeznikova.model.aop.MethodMetric;
import ru.kpfu.itis.Kolodeznikova.repository.aop.MethodMetricRepository;


@Aspect
@Component
public class MetricsAspect {

    private final MethodMetricRepository repository;

    public MetricsAspect(MethodMetricRepository repository) {
        this.repository = repository;
    }

    @Pointcut("@annotation(Metric)")
    public void metricAnnotated() {}

    @Around("metricAnnotated()")
    public Object trackMetrics(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        MethodMetric metric = repository.findById(methodName).orElse(new MethodMetric(methodName));

        try {
            Object result = joinPoint.proceed();
            metric.incrementSuccess();
            repository.save(metric);
            return result;
        } catch (Throwable e) {
            metric.incrementFailure();
            repository.save(metric);
            throw e;
        }
    }
}
