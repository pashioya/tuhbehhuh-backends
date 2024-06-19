package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ChangeEndpointFrequencyCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.ChangeEndpointFrequencyUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointChangeFrequencyPort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultChangeEndpointFrequencyUseCase implements ChangeEndpointFrequencyUseCase {

    private final EndpointLoadPort endpointLoadPort;
    private final List<EndpointChangeFrequencyPort> endpointChangeFrequencyPorts;

    @Override
    public Optional<Endpoint> changeFrequency(ChangeEndpointFrequencyCommand command) {
        var possibleEndpoint = endpointLoadPort.loadEndpoint(command.endpointUUID());
        if (possibleEndpoint.isEmpty()) {
            return possibleEndpoint;
        }

        var endpoint = possibleEndpoint.get();
        endpoint.changeFrequency(command.timeInterval(), command.timeUnit());

        endpointChangeFrequencyPorts.forEach(p ->
                p.changeFrequency(endpoint)
        );
        return endpointLoadPort.loadEndpoint(command.endpointUUID());
    }
}
