package be.kdg.integration5.extractor.data_importer.core;

import be.kdg.integration5.extractor.data_importer.adapters.in.events.ResponseCreatedEvent;
import be.kdg.integration5.extractor.data_importer.domain.ResponseData;
import be.kdg.integration5.extractor.data_importer.ports.in.projection.ResponseCreatedProjector;
import be.kdg.integration5.extractor.data_importer.ports.in.usecase.ResponseCreatedUseCase;
import be.kdg.integration5.extractor.data_importer.ports.out.ResponseCreatePort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DefaultResponseCreatedUseCase implements ResponseCreatedUseCase {
    private final static Logger log = LoggerFactory.getLogger(DefaultResponseCreatedUseCase.class);
    private final ResponseCreatedProjector responseCreatedProjector;
    private final List<ResponseCreatePort> responseCreatePorts;
    @Override
    public void createResponse(ResponseCreatedEvent event) {
        var data = responseCreatedProjector.project(event);
        responseCreatePorts.forEach(p -> p.createResponse(data));
    }
}
