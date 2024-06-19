package be.kdg.integration5.extractor.extractor_backend.domain;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointResponseJpaEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class EndpointResponse {
    public record EndpointResponseUUID(UUID uuid){}
    private EndpointResponseUUID uuid;
    private Endpoint.EndpointUUID endpointUUID;
    private LocalDateTime timeSent;
    private Optional<String> body;
    private String request;
    private int statusCode;
    private URI answeringUri;


    public EndpointResponse(ResponseData responseData) {
        this.uuid = responseData.uuid();
        this.endpointUUID = responseData.endpointUUID();
        this.timeSent = responseData.timeSent();
        if (responseData.statusCode() > 299) {
            this.body = Optional.of(responseData.body());
        } else {
            this.body = Optional.empty();
        }
        this.request = responseData.request();
        this.statusCode = responseData.statusCode();
        this.answeringUri = responseData.answeringUri();
    }
}