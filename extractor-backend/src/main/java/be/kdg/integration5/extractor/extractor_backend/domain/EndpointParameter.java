package be.kdg.integration5.extractor.extractor_backend.domain;

import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateEndpointCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndpointParameter {

    private EndpointParameterUUID uuid;
    private Endpoint.EndpointUUID parentUuid;
    private String parameterKey;
    private String parameterValue;

    public record EndpointParameterUUID(UUID uuid) {
    }

}
