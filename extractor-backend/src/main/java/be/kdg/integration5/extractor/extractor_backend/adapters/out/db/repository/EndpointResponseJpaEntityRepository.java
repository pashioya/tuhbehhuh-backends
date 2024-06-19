package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointResponseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EndpointResponseJpaEntityRepository extends JpaRepository<EndpointResponseJpaEntity, UUID> {

    @Query("SELECT e FROM EndpointResponseJpaEntity e WHERE e.endpointUUID = :endpointUUID")
    Optional<List<EndpointResponseJpaEntity>> findAllByEndpointUUID(UUID endpointUUID);
}
