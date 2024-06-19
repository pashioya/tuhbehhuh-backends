package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateEndpointCommand;

import java.util.Optional;

public interface CreateEndpointUseCase {
    Optional<Endpoint> createEndpoint(CreateEndpointCommand command);
}
