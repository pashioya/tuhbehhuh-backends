package be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.DeactivateEndpointCommand;

public interface EndpointDeactivatePort {

    void deactivatePort(Endpoint endpoint);
}
