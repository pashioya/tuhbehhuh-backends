package be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

public interface EndpointCreatePort {

    void createEndpoint(Endpoint endPoint);
}
