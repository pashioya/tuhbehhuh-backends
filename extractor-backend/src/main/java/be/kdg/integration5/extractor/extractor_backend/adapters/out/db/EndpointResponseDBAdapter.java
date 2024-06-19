package be.kdg.integration5.extractor.extractor_backend.adapters.out.db;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointResponseJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.EndpointResponseJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;
import be.kdg.integration5.extractor.extractor_backend.domain.ResponseData;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse.EndpointResponseCreatePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse.EndpointResponseLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class EndpointResponseDBAdapter implements EndpointResponseCreatePort, EndpointResponseLoadPort {
    private final EndpointResponseJpaEntityRepository endpointResponseJpaEntityRepository;


    @Override
    public void createResponse(ResponseData responseData) {
        endpointResponseJpaEntityRepository.save(new EndpointResponseJpaEntity(new EndpointResponse(responseData)));
    }

    @Override
    public Optional<List<EndpointResponseJpaEntity>> findAllByEndpointUUID(UUID endpointUUID) {
            return endpointResponseJpaEntityRepository.findAllByEndpointUUID(endpointUUID);
    }
}
