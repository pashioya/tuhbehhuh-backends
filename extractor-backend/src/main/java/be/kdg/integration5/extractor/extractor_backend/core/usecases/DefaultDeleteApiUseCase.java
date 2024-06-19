package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.DeleteApiUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiDeletePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiLoadPort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointDeletePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultDeleteApiUseCase implements DeleteApiUseCase {

    private final ApiLoadPort apiLoadPort;
    private final List<ApiDeletePort> apiDeletePorts;
    private final List<EndpointDeletePort> endpointDeletePorts;


    @Override
    public Optional<Api> deleteApi(Api.ApiUUID apiUUID) {
        var api = apiLoadPort.findById(apiUUID);
        if (api.isEmpty()) {
            return api;
        }
        apiDeletePorts.forEach(p -> p.deleteApi(api.get()));
        endpointDeletePorts.forEach(p -> p.deleteByApi(api.get()));
        return api;
    }
}
