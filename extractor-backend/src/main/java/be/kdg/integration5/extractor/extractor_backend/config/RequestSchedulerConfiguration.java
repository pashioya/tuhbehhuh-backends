package be.kdg.integration5.extractor.extractor_backend.config;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@Configuration
public class RequestSchedulerConfiguration {

    @Bean
    ScheduledExecutorService createExecutorService() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    Map<Endpoint.EndpointUUID, ScheduledFuture<?>> getRunningTasks() {
        return new HashMap<>();
    }
}
