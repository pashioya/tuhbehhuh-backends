package be.kdg.integration5.extractor.extractor_backend.ports.in.query;

import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;

import java.util.List;
import java.util.UUID;

public interface EndpointResponseQuery {

    List<EndpointResponse> findAllByEndpointUUID(UUID endpointUUID);
}
