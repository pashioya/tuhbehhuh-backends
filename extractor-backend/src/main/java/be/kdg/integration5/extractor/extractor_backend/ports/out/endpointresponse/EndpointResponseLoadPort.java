package be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointResponseJpaEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EndpointResponseLoadPort {

    Optional<List<EndpointResponseJpaEntity>> findAllByEndpointUUID(UUID endpointUUID);
}
