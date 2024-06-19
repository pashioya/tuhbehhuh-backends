package be.kdg.integration5.extractor.data_importer.ports.in.usecase;

import be.kdg.integration5.extractor.data_importer.adapters.in.events.ResponseCreatedEvent;

public interface ResponseCreatedUseCase {
    void createResponse(ResponseCreatedEvent event);
}
