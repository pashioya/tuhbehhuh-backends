package be.kdg.integration5.extractor.extractor_backend.core.queries;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointResponseJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.EndpointResponseQuery;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse.EndpointResponseLoadPort;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DefaultEndpointResponseQuery implements EndpointResponseQuery {

    private final EndpointResponseLoadPort endpointResponseLoadPort;
    private final ModelMapper modelMapper;

    @Override
    public List<EndpointResponse> findAllByEndpointUUID(UUID endpointUUID) {
            List<EndpointResponseJpaEntity> endpointResponseJpaEntities = endpointResponseLoadPort.findAllByEndpointUUID(endpointUUID).orElse(Collections.emptyList());
            return endpointResponseJpaEntities.stream()
                .map(e -> modelMapper.map(e, EndpointResponse.class))
                .collect(Collectors.toList());
    }
}
