package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.ApiJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApiJpaEntityRepository extends JpaRepository<ApiJpaEntity, UUID> {

    @Query("""
        select api
        from ApiJpaEntity api
        join EndpointJpaEntity e
            on api.uuid = e.apiUuid
        where e.active = true
    """)
    List<ApiJpaEntity> findByActiveEndpoints();


    @Query("""
        select api
        from ApiJpaEntity api
        join EndpointJpaEntity e
            on api.uuid = e.apiUuid
        where e.uuid = :uuid
    """)
    Optional<ApiJpaEntity> findByEndpointUuid(UUID uuid);



}
