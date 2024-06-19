package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.DeleteEndpointUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointDeletePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultDeleteEndpointUseCase implements DeleteEndpointUseCase {
    private final EndpointLoadPort endpointLoadPort;
    private final List<EndpointDeletePort> endpointDeletePorts;

    @Override
    public Optional<Endpoint> deleteEndpoint(Endpoint.EndpointUUID endpointUUID) {
        var endpoint = endpointLoadPort.loadEndpoint(endpointUUID);
        if (endpoint.isEmpty()) {
            return endpoint;
        }
        endpointDeletePorts.forEach(p -> p.deleteEndpoint(endpoint.get()));
        return endpoint;
    }
}
