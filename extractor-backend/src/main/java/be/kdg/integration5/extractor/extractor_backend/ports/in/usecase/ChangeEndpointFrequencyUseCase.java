package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ChangeEndpointFrequencyCommand;

import java.util.Optional;

public interface ChangeEndpointFrequencyUseCase {
    Optional<Endpoint> changeFrequency(ChangeEndpointFrequencyCommand command);
}
