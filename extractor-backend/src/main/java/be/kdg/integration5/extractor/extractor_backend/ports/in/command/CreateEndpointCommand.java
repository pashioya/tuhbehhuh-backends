package be.kdg.integration5.extractor.extractor_backend.ports.in.command;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import java.util.List;
import java.util.concurrent.TimeUnit;

public record CreateEndpointCommand(
        String name,
        Api.ApiUUID apiUUID,
        String endpointPath,
        List<EndpointParameter> requestParams,
        boolean active,
        int timeInterval,
        TimeUnit timeUnit) {
    public record EndpointParameter(String parameterKey, String parameterVal) {
    }
}
