package com.kasean.spring.boot.homework.others.metrics;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean serviceAvailable = checkExternalService();

        if (serviceAvailable) {
            return Health.up()
                    .withDetail("externalService", "Available")
                    .withDetail("timestamp", System.currentTimeMillis())
                    .build();
        } else {
            return Health.down()
                    .withDetail("externalService", "Unavailable")
                    .withDetail("error", "Service connection failed")
                    .withDetail("timestamp", System.currentTimeMillis())
                    .build();
        }
    }

    private boolean checkExternalService() {
        return Math.random() > 0.3;
    }
}
