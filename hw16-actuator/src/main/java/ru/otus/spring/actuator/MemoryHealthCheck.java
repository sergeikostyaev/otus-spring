package ru.otus.spring.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthCheck implements HealthIndicator {
    @Override
    public Health health() {

        long totalMemory = Runtime.getRuntime().totalMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        long usedMemory = totalMemory - freeMemory;
        double memoryUsagePercentage = (double) usedMemory / totalMemory * 100;

        if (memoryUsagePercentage > 90) {
            return Health.down()
                    .status(Status.UP)
                    .withDetail("message", "Problems with memory")
                    .build();
        } else {
            return Health.up().withDetail("message", "No problems with memory").build();
        }
    }
}