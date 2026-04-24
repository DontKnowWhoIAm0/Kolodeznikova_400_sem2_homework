package ru.kpfu.itis.Kolodeznikova.repository.aop;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.Kolodeznikova.model.aop.ExecutionTime;

import java.util.List;

public interface ExecutionTimeRepository extends JpaRepository<ExecutionTime, Long> {
    List<ExecutionTime> findByMethodName(String methodName);
}
