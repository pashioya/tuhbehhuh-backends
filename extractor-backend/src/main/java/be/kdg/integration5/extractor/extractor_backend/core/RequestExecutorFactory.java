package be.kdg.integration5.extractor.extractor_backend.core;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse.EndpointResponseCreatePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RequestExecutorFactory {

    private final List<EndpointResponseCreatePort> endpointResponseCreatePorts;

    public RequestExecutor getExecutor(Endpoint endPoint) {
        return new RequestExecutor(this.endpointResponseCreatePorts, endPoint);
    }
}
