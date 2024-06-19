package be.kdg.integration5.extractor.extractor_backend.ports.out;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.List;

public interface StartUpRequestSchedulesPort {
    void startUpRequestSchedules(List<Endpoint> endpointList);
}
