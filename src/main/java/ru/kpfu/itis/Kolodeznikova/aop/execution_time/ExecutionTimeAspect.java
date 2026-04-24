package ru.kpfu.itis.Kolodeznikova.aop.execution_time;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.Kolodeznikova.model.aop.ExecutionTime;
import ru.kpfu.itis.Kolodeznikova.repository.aop.ExecutionTimeRepository;

@Aspect
@Component
public class ExecutionTimeAspect {

    private final ExecutionTimeRepository repository;

    public ExecutionTimeAspect(ExecutionTimeRepository repository) {
        this.repository = repository;
    }

    @Pointcut("@annotation(ExecutionTime)")
    public void metricAnnotated() {}

    @Around("metricAnnotated()")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long duration = end - start;

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            String methodName = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

            repository.save(new ExecutionTime(methodName, duration));
        }

        return result;
    }
}