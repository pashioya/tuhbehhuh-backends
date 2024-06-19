package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity;

import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table
@Entity
@Setter
public class EndpointResponseJpaEntity {

    @Id @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    @JdbcTypeCode(Types.VARCHAR)
    private UUID endpointUUID;
    private LocalDateTime timeReceived;
    private String body;
    private String request;
    private int statusCode;
    private String answeringUri;


    public EndpointResponseJpaEntity(EndpointResponse response) {
        this.uuid = response.getUuid().uuid();
        this.endpointUUID = response.getEndpointUUID().uuid();
        this.timeReceived = response.getTimeSent();
        this.body = response.getBody().orElse(null);
        this.request = response.getRequest();
        this.statusCode = response.getStatusCode();
        this.answeringUri = response.getAnsweringUri().toString();
    }
}
