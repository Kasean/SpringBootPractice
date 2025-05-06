package com.kasean.spring.boot.homework.others.metrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/demo")
    public String demo() {
        metricsService.recordOperation();
        metricsService.recordBusinessMetric("sample_metric", Math.random() * 100);
        return "Operation recorded";
    }
}
