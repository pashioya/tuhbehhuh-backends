package be.kdg.integration5.extractor.extractor_backend.domain;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.ApiJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateApiCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Api {

    public record ApiUUID(UUID uuid){
        public ApiUUID(String strUuid){
            this(UUID.fromString(strUuid));
        }
    }

    private ApiUUID uuid;
    private String name;
    private URL vendorUrl;

    private Optional<String> apiKeyParameterName;
    private Optional<String> apiKey;
    private Optional<Integer> maxRequestsPerDay;


    public Api(UUID uuid, String name, String url, String apiKeyParameterName, String apiKey) throws MalformedURLException {
        this.uuid = new ApiUUID(uuid);
        this.name = name;
        this.vendorUrl = new URL(url);
        this.apiKeyParameterName = Optional.of(apiKeyParameterName);
        this.apiKey = Optional.of(apiKey);
        this.maxRequestsPerDay = Optional.empty();
    }

    public Api(UUID uuid, String name, String url) throws MalformedURLException {
        this.name = name;
        this.uuid = new ApiUUID(uuid);
        this.vendorUrl = new URL(url);
        this.apiKeyParameterName = Optional.empty();
        this.apiKey = Optional.empty();
        this.maxRequestsPerDay = Optional.empty();
    }
    public Api(String url, String name) throws MalformedURLException {
        this(UUID.randomUUID(), name, url);
    }


}
