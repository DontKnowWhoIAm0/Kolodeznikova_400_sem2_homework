package ru.kpfu.itis.Kolodeznikova.controller.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.Kolodeznikova.model.aop.ExecutionTime;
import ru.kpfu.itis.Kolodeznikova.repository.aop.ExecutionTimeRepository;

import java.util.*;

@RestController
public class ExecutionTimeController  {

    private final ExecutionTimeRepository repository;

    public ExecutionTimeController(ExecutionTimeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/execution-time")
    public Map<String, Object> getExecutionTime(@RequestParam String methodName, @RequestParam int percentile) {
        List<ExecutionTime> executions = repository.findByMethodName(methodName);

        List<Long> durations = executions.stream()
                .map(ExecutionTime::getDurationMs).sorted().toList();

        Map<String, Object> response = new HashMap<>();
        response.put("method", methodName);
        response.put("count", durations.size());

        if (!durations.isEmpty()) {
            int index = (int) Math.ceil(percentile / 100.0 * durations.size()) - 1;
            index = Math.max(0, Math.min(index, durations.size() - 1));
            response.put(percentile + "th_percentile", durations.get(index));

            response.put("average", durations.stream().mapToLong(Long::longValue).average().orElse(0));
            response.put("max", durations.getLast());
            response.put("min", durations.getFirst());
        } else {
            response.put(percentile + "th_percentile", null);
            response.put("average", null);
            response.put("max", null);
            response.put("min", null);
        }
        return response;
    }
}
