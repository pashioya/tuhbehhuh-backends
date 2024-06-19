package be.kdg.integration5.extractor.extractor_backend.ports.in.command;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

public record ActivateEndpointCommand(
        Endpoint.EndpointUUID endpointUUID
){
}
