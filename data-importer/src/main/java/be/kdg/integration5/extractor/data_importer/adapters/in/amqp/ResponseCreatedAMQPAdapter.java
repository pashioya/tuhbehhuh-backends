package be.kdg.integration5.extractor.data_importer.adapters.in.amqp;

import be.kdg.integration5.extractor.data_importer.adapters.in.ResponseEventHandler;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.Event;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.EventCatalog;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.ResponseCreatedEvent;
import be.kdg.integration5.extractor.data_importer.ports.in.usecase.ResponseCreatedUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ResponseCreatedAMQPAdapter implements ResponseEventHandler<ResponseCreatedEvent> {
    private final static Logger log = LoggerFactory.getLogger(ResponseCreatedAMQPAdapter.class);
    private final ResponseCreatedUseCase responseCreatedUseCase;
    private final ObjectMapper objectMapper;
    @Override
    public boolean appliesTo(EventCatalog eventCatalog) {
        return EventCatalog.RESPONSE_CREATED == eventCatalog;
    }

    @Override
    public Event map(String eventBody) {
        try {
            return objectMapper.readValue(eventBody, ResponseCreatedEvent.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handle(Event responseEventBody) {
        responseCreatedUseCase.createResponse((ResponseCreatedEvent) responseEventBody);
    }
}
