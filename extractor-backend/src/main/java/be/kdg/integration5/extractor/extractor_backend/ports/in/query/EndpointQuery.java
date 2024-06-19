package be.kdg.integration5.extractor.extractor_backend.ports.in.query;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.List;
import java.util.Optional;

public interface EndpointQuery {
    List<Endpoint> findAll();
    Optional<Endpoint> findEndpoint(Endpoint.EndpointUUID uuid);

    List<Endpoint> findAllEndpointsForApi(Api.ApiUUID apiUUID);

    List<Endpoint> findAllActiveEndpoints();

}
