package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses.EndpointDto;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ActivateEndpointCommand;

import java.util.Optional;

public interface ActivateEndpointUseCase {

    Optional<Endpoint> activateEndpointUseCase(ActivateEndpointCommand command);
}
