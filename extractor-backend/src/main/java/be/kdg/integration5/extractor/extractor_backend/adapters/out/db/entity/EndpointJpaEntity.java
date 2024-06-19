package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Entity
public class EndpointJpaEntity {
    @Id @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    private String name;
    @JdbcTypeCode(Types.VARCHAR)
    private UUID apiUuid;
    private String endpointPath;
    private boolean active;
    private int timeInterval;
    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;

    public EndpointJpaEntity(Endpoint endpoint) {
        this.uuid = endpoint.getUuid().uuid();
        this.name = endpoint.getName();
        this.apiUuid = endpoint.getApi().getUuid().uuid();
        this.endpointPath = endpoint.getEndpointPath();
        this.active = endpoint.isActive();
        this.timeInterval = endpoint.getTimeInterval();
        this.timeUnit = endpoint.getTimeUnit();
    }
}
