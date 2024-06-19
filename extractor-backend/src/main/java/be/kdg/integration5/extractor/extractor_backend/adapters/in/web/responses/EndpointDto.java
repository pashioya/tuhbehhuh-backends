package be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public record EndpointDto(

        UUID uuid,
        String name,
        UUID apiUuid,
        String endpointPath,
        List<EndpointParameterDto>parameters,
        boolean active,
        int timeInterval,
        TimeUnit timeUnit
) {
    public EndpointDto(Endpoint endpoint) {
        this(
                endpoint.getUuid().uuid(),
                endpoint.getName(),
                endpoint.getApi().getUuid().uuid(),
                endpoint.getEndpointPath(),
                endpoint.getParameters() != null ? endpoint.getParameters().stream().map(EndpointParameterDto::new).collect(Collectors.toList()) : Collections.emptyList(),
                endpoint.isActive(),
                endpoint.getTimeInterval(),
                endpoint.getTimeUnit()
        );
    }

}
