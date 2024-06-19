package be.kdg.integration5.extractor.extractor_backend.core.queries;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.EndpointQuery;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class DefaultEndpointQuery implements EndpointQuery {

    private final EndpointLoadPort endpointLoadPort;

    @Override
    public List<Endpoint> findAll() {
        return endpointLoadPort.loadAllEndpoints();
    }

    @Override
    public Optional<Endpoint> findEndpoint(Endpoint.EndpointUUID uuid) {
        return endpointLoadPort.loadEndpoint(uuid);
    }

    @Override
    public List<Endpoint> findAllEndpointsForApi(Api.ApiUUID apiUUID) {
        return endpointLoadPort.loadAllEndpointsForApi(apiUUID);
    }

    @Override
    public List<Endpoint> findAllActiveEndpoints() {
        return endpointLoadPort.loadAllActiveEndpoints();
    }
}
