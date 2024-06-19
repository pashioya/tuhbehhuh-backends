package be.kdg.integration5.extractor.data_importer.core;

import be.kdg.integration5.extractor.data_importer.adapters.in.events.ResponseCreatedEvent;
import be.kdg.integration5.extractor.data_importer.domain.ResponseData;
import be.kdg.integration5.extractor.data_importer.ports.in.projection.ResponseCreatedProjector;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DefaultResponseCreatedProjector implements ResponseCreatedProjector {

    @Override
    public ResponseData project(ResponseCreatedEvent event) {
        return new ResponseData(event);
    }
}
