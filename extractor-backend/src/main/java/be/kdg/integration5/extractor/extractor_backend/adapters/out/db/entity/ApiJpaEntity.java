package be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity;

import be.kdg.integration5.extractor.extractor_backend.config.AttributeEncrypter;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Entity
@Setter
public class ApiJpaEntity {
    @Id @JdbcTypeCode(Types.VARCHAR)
    private UUID uuid;
    private String name;
    private String vendorUrl;
    private String apiKeyParameterName;
    //FIXME add a proper key
    @Convert(converter = AttributeEncrypter.class)
    private String apiKey;
    private int maxRequestsPerDay;

    public ApiJpaEntity(Api api) {
        this.uuid = api.getUuid().uuid();
        this.name = api.getName();
        this.vendorUrl = api.getVendorUrl().toString();
        this.apiKeyParameterName = api.getApiKeyParameterName().orElse(null);
        this.apiKey = api.getApiKey().orElse(null);
        this.maxRequestsPerDay = api.getMaxRequestsPerDay().orElse(0);
    }
}
