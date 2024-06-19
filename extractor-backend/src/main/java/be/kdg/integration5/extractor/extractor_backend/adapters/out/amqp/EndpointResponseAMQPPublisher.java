package be.kdg.integration5.extractor.extractor_backend.adapters.out.amqp;

import be.kdg.integration5.extractor.extractor_backend.adapters.config.RabbitMQModuleTopology;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.events.EventCatalog;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.events.EventHeader;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.events.EventMessage;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.events.ResponseCreatedEvent;
import be.kdg.integration5.extractor.extractor_backend.domain.ResponseData;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse.EndpointResponseCreatePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.UUID;

@AllArgsConstructor
@Component
public class EndpointResponseAMQPPublisher implements EndpointResponseCreatePort {
    private static final Logger log = LoggerFactory.getLogger(EndpointResponseAMQPPublisher.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void createResponse(ResponseData responseData) {
        var eventHeader = EventHeader.builder()
                .eventID(UUID.randomUUID())
                .eventCatalog(EventCatalog.RESPONSE_CREATED)
                .build();
        var eventBody = new ResponseCreatedEvent(responseData);

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQModuleTopology.ENDPOINT_RESPONSES_FAN_OUT,
                    "response.created",
                    EventMessage.builder()
                            .eventHeader(eventHeader)
                            .eventBody(objectMapper.writeValueAsString(eventBody))
                            .build()
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
