package be.kdg.integration5.extractor.extractor_backend.ports.in.command;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

public record DeactivateEndpointCommand(
        Endpoint.EndpointUUID uuid
) {
}
