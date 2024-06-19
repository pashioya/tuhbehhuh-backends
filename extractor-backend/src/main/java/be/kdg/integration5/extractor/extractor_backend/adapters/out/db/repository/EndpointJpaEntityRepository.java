package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointJpaEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EndpointJpaEntityRepository extends JpaRepository<EndpointJpaEntity, UUID> {

    @Query("""
        select e
        from EndpointJpaEntity e
        where e.active = true
    """)
    List<EndpointJpaEntity> findByActive();

    @Query("""
        select e
        from EndpointJpaEntity e
        where e.apiUuid = :apiUuid
    """)
    List<EndpointJpaEntity> findByApi(UUID apiUuid);


    @Modifying
    @Transactional
    @Query("""
        delete
        from EndpointJpaEntity e
        where e.apiUuid = :apiUUID
    """)
    void deleteByApi(UUID apiUUID);
}
