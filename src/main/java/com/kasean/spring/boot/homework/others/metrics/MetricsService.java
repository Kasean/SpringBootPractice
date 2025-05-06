package com.kasean.spring.boot.homework.others.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {

    private final Counter customCounter;
    private final MeterRegistry meterRegistry;

    public MetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.customCounter = Counter
                .builder("custom.operation.count")
                .description("Counts custom operations")
                .tags("type", "operation")
                .register(meterRegistry);
    }

    public void recordOperation() {
        customCounter.increment();

        meterRegistry.timer("custom.operation.duration")
                .record(() -> {
                    try {
                        Thread.sleep((long) (Math.random() * 100));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
    }

    public void recordBusinessMetric(String metricName, double value) {
        io.micrometer.core.instrument.Gauge.builder("business.metric", value, v -> v)
                .tags("name", metricName)
                .description("Business specific metric")
                .register(meterRegistry);
    }
}
