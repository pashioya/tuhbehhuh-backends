package be.kdg.integration5.extractor.extractor_backend.domain;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseData (
        EndpointResponse.EndpointResponseUUID uuid,
        Endpoint.EndpointUUID endpointUUID,
        int statusCode,
        String request,
        URI answeringUri,
        LocalDateTime timeSent,
        String body
){
    public ResponseData(
            Endpoint.EndpointUUID endpointUUID,
            HttpResponse<String> response,
            LocalDateTime timeReceived
    ) {
        this(
                new EndpointResponse.EndpointResponseUUID(UUID.randomUUID()),
                endpointUUID,
                response.statusCode(),
                response.request().uri().toString(),
                response.uri(),
                timeReceived,
                response.body()
        );
    }
}
