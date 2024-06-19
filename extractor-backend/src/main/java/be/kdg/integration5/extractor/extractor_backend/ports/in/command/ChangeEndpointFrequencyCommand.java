package be.kdg.integration5.extractor.extractor_backend.ports.in.command;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;

import java.util.concurrent.TimeUnit;

public record ChangeEndpointFrequencyCommand(
        Endpoint.EndpointUUID endpointUUID,
        int timeInterval,
        TimeUnit timeUnit
){
}
