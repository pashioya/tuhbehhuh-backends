package be.kdg.integration5.extractor.extractor_backend.adapters.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQModuleTopology {
    public static final String ENDPOINT_RESPONSES_FAN_OUT = "endpoint-response-events";
    public static final String ENDPOINT_RESPONSES_QUEUE = "endpoint-response-events-queue";

    @Bean
    FanoutExchange endpointResponsesExchange() {
        return new FanoutExchange(ENDPOINT_RESPONSES_FAN_OUT);
    }

    @Bean
    Queue endpointResponseQueue() {
        return new Queue(ENDPOINT_RESPONSES_QUEUE);
    }

    @Bean
    Binding endpointResponseBinding(FanoutExchange endpointResponseExchange, Queue endpointResponseQueue){
        return BindingBuilder.bind(endpointResponseQueue).to(endpointResponseExchange);
    }
}
