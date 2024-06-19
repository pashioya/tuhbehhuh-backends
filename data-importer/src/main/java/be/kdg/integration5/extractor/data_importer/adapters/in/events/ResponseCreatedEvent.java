package be.kdg.integration5.extractor.data_importer.adapters.in.events;

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
) implements Event{
}
