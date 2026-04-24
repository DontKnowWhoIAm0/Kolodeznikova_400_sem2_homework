package ru.kpfu.itis.Kolodeznikova.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.Kolodeznikova.aop.execution_time.ExecutionTime;
import ru.kpfu.itis.Kolodeznikova.aop.logs.Loggable;
import ru.kpfu.itis.Kolodeznikova.aop.metrics.Metric;

@Controller

public class IndexController {

    @Loggable
    @Metric
    @ExecutionTime
    @GetMapping(value = "index")
    public String index() {
        return "index";
    }
}
