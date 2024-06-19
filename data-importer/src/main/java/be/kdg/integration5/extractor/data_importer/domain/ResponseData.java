package be.kdg.integration5.extractor.data_importer.domain;

import be.kdg.integration5.extractor.data_importer.adapters.in.events.ResponseCreatedEvent;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseData(
        UUID uuid,
        UUID endpointUUID,
        int statusCode,
        String request,
        URI answeringUri,
        LocalDateTime timeSent,
        String body
) {
    public ResponseData(ResponseCreatedEvent event) {
        this(
                event.uuid(),
                event.endpointUUID(),
                event.statusCode(),
                event.request(),
                event.answeringUri(),
                event.timeSent(),
                event.body()
        );
    }
}
