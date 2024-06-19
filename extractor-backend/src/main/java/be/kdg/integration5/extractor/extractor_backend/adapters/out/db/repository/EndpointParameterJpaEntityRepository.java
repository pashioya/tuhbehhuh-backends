package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointParameterJpaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EndpointParameterJpaEntityRepository extends JpaRepository<EndpointParameterJpaEntity, UUID> {

    @Query("""
        select ep
        from EndpointParameterJpaEntity ep
        join EndpointJpaEntity e
            on e.uuid = ep.endpointUuid
        where
            e.active = true
    """)
    List<EndpointParameterJpaEntity> findByActiveEndpoints();

    @Query("""
        select ep
        from EndpointParameterJpaEntity ep
        where ep.endpointUuid = :uuid
    """)
    List<EndpointParameterJpaEntity> findByEndpointUuid(UUID uuid);

    @Query("""
        select ep
        from EndpointParameterJpaEntity ep
        where ep.endpointUuid in (:endpointUuids)
    """)
    List<EndpointParameterJpaEntity> findByInEndpointUuids(List<UUID> endpointUuids);

    @Modifying
    @Transactional
    @Query("delete from EndpointParameterJpaEntity ep " +
           "where ep.endpointUuid in (select e.uuid from EndpointJpaEntity e where e.uuid = :endpointUUID)")
    void deleteByEndpoint( UUID endpointUUID);

    @Modifying
    @Transactional
    @Query("""
        delete from EndpointParameterJpaEntity ep\s
        where ep.endpointUuid in (
            select e.uuid\s
            from EndpointJpaEntity e\s
            where e.apiUuid = :apiUUID
        )
    """)
    void deleteByApi(UUID apiUUID);



}
