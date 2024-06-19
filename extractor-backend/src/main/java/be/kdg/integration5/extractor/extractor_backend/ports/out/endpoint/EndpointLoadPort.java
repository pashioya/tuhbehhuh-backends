package be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.List;
import java.util.Optional;

public interface EndpointLoadPort {

    Optional<Endpoint> loadEndpoint(Endpoint.EndpointUUID uuid);

    List<Endpoint> loadAllEndpointsForApi(Api.ApiUUID apiUUID);

    List<Endpoint> loadAllActiveEndpoints();

    List<Endpoint> loadAllEndpoints();


}
