package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.Optional;

public interface DeleteEndpointUseCase {
    Optional<Endpoint> deleteEndpoint(Endpoint.EndpointUUID endpointUUID);
}
