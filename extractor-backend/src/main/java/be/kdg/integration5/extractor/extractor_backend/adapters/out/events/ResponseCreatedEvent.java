package be.kdg.integration5.extractor.extractor_backend.adapters.out.events;

import be.kdg.integration5.extractor.extractor_backend.domain.ResponseData;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

public record ResponseCreatedEvent(
        UUID uuid,
        UUID endpointUUID,
        int statusCode,
        String request,
        URI answeringUri,
        LocalDateTime timeSent,
        String body
) implements Event {
    public ResponseCreatedEvent(ResponseData data) {
        this(
                data.uuid().uuid(),
                data.endpointUUID().uuid(),
                data.statusCode(),
                data.request(),
                data.answeringUri(),
                data.timeSent(),
                data.body()
        );
    }
}
