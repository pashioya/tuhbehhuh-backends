package be.kdg.integration5.extractor.data_importer.adapters.in;

import be.kdg.integration5.extractor.data_importer.adapters.in.events.Event;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.EventCatalog;
import be.kdg.integration5.extractor.data_importer.adapters.in.events.EventMessage;

public interface ResponseEventHandler<T extends Event> {

    boolean appliesTo(EventCatalog eventCatalog);

    default ResponseEventHandler<T> recive(EventMessage eventMessage) {
        return this;
    }

    Event map(String eventBody);
    void handle(Event responseEventBody);

}
