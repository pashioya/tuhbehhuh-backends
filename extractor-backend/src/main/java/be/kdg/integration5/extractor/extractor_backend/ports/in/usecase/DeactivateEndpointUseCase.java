package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.DeactivateEndpointCommand;

import java.util.Optional;

public interface DeactivateEndpointUseCase {

    Optional<Endpoint> deactivateEndpoint(DeactivateEndpointCommand command);
}
