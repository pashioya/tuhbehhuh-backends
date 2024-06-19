package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.DeactivateEndpointCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.DeactivateEndpointUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointDeactivatePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultDeactivateEndpointUseCase implements DeactivateEndpointUseCase {

    private final List<EndpointDeactivatePort> deactivatePorts;
    private final EndpointLoadPort endpointLoadPort;
    @Override
    public Optional<Endpoint> deactivateEndpoint(DeactivateEndpointCommand command) {
        var possibleEndpoint = endpointLoadPort.loadEndpoint(command.uuid());
        if (possibleEndpoint.isEmpty()) {
            return Optional.empty();
        }

        var endpoint = possibleEndpoint.get();
        endpoint.deactivate();
        deactivatePorts.forEach(p -> p.deactivatePort(endpoint));
        return Optional.of(endpoint);
    }
}
