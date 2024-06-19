package be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.EndpointQuery;

import java.util.List;

public interface EndpointDeletePort {

    void deleteEndpoint(Endpoint endpoint);

    void deleteByApi(Api api);
}
