package be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.concurrent.TimeUnit;

public interface EndpointChangeFrequencyPort {
    void changeFrequency(Endpoint endpoint);
}
