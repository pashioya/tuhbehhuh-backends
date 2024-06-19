package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class EndpointParameterJpaEntity {

    @Id @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    @JdbcTypeCode(Types.VARCHAR)
    private UUID endpointUuid;
    private String parameterKey;
    private String parameterValue;
}
