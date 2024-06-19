package be.kdg.integration5.extractor.extractor_backend.adapters.in.web.requests;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.TimeUnit;

public record ChangeFrequencyDto(
        int timeInterval,
        TimeUnit timeUnit
) {
}
