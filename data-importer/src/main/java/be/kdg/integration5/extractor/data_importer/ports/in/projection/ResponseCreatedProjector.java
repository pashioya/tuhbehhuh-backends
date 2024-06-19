package be.kdg.integration5.extractor.data_importer.ports.in.projection;

import be.kdg.integration5.extractor.data_importer.adapters.in.events.ResponseCreatedEvent;
import be.kdg.integration5.extractor.data_importer.domain.ResponseData;

public interface ResponseCreatedProjector {

    ResponseData project(ResponseCreatedEvent event);
}
