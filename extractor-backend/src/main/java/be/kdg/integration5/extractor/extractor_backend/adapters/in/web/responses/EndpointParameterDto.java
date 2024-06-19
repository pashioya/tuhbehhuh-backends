package be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses;

import be.kdg.integration5.extractor.extractor_backend.domain.EndpointParameter;

import java.util.UUID;

public record EndpointParameterDto(
        UUID uuid,
        UUID parentUuid,
        String parameterKey,
        String parameterValue
) {

    public EndpointParameterDto(EndpointParameter endpointParameter) {
        this(
                endpointParameter.getUuid().uuid(),
                endpointParameter.getParentUuid().uuid(),
                endpointParameter.getParameterKey(),
                endpointParameter.getParameterValue()
        );
    }
}
