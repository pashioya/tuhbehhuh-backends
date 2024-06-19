package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointParameter;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateEndpointCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.CreateEndpointUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiLoadPort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointCreatePort;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultCreateEndpointUseCase implements CreateEndpointUseCase {

    private final ApiLoadPort apiLoadPort;
    private final List<EndpointCreatePort> endpointCreatePortList;
    private final ModelMapper modelMapper;
    @Override
    public Optional<Endpoint> createEndpoint(CreateEndpointCommand command) {
        var api = apiLoadPort.findById(command.apiUUID());

        if (api.isEmpty()) {
            return Optional.empty();
        }
        var endpoint = modelMapper.map(command, Endpoint.class);
        endpoint.setParameters(command.requestParams().stream().map(p -> modelMapper.map(p, EndpointParameter.class)).toList());
        endpoint.setApi(api.get());
        endpointCreatePortList.forEach(e -> e.createEndpoint(endpoint));
        return Optional.of(endpoint);
    }
}
