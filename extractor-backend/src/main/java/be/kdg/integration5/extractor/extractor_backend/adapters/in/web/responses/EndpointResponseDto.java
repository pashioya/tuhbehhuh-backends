package be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;

import java.net.URI;
import java.time.LocalDateTime;

public record EndpointResponseDto(EndpointResponse.EndpointResponseUUID uuid,
        Endpoint.EndpointUUID endpointUUID,
        int statusCode,
        String request,
        URI answeringUri,
        LocalDateTime timeSent,
        String body) {

    public EndpointResponseDto(EndpointResponse endpointResponse) {
    this(
            endpointResponse.getUuid(),
            endpointResponse.getEndpointUUID(),
            endpointResponse.getStatusCode(),
            endpointResponse.getRequest(),
            endpointResponse.getAnsweringUri(),
            endpointResponse.getTimeSent(),
            String.valueOf(endpointResponse.getBody())
    );
    }
}
