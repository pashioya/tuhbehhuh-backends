package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ActivateEndpointCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.ActivateEndpointUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointActivatePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultActivateEndpointUseCase implements ActivateEndpointUseCase {

    private final List<EndpointActivatePort> activatePorts;
    private final EndpointLoadPort endpointLoadPort;
    @Override
    public Optional<Endpoint> activateEndpointUseCase(ActivateEndpointCommand command) {
        var possibleEndpoint = endpointLoadPort.loadEndpoint(command.endpointUUID());
        if (possibleEndpoint.isEmpty()) {
            return possibleEndpoint;
        }
        var endpoint = possibleEndpoint.get();
        endpoint.activate();
        activatePorts.forEach(p -> p.activateEndpoint(endpoint));
        return Optional.of(endpoint);
    }
}
