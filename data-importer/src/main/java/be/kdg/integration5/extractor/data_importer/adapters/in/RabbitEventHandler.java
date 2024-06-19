package be.kdg.integration5.extractor.data_importer.adapters.in;

import be.kdg.integration5.extractor.data_importer.adapters.config.RabbitMQModuleTopology;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.Event;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.EventMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class RabbitEventHandler {
    private final static Logger log = LoggerFactory.getLogger(RabbitEventHandler.class);
    private final List<ResponseEventHandler<? extends Event>> responseEventHandlers;

    @RabbitListener(queues = RabbitMQModuleTopology.ENDPOINT_RESPONSES_QUEUE, messageConverter = "#{jackson2JsonMessageConverter}")
    public void reciveEventMessage(EventMessage eventMessage) {
        responseEventHandlers.stream()
                .filter(responseEventHandler ->
                        responseEventHandler.appliesTo(eventMessage.getEventHeader().getEventCatalog())
                )
                .forEach(responseEventHandler ->
                        responseEventHandler.recive(eventMessage).handle(
                                responseEventHandler.map(eventMessage.getEventBody())
                        )
                );
    }
}
