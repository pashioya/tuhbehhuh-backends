package be.kdg.integration5.extractor.extractor_backend.adapters.in.web;

import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.requests.ChangeFrequencyDto;
import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses.EndpointDto;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ActivateEndpointCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ChangeEndpointFrequencyCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateEndpointCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.DeactivateEndpointCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.EndpointQuery;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/endpoints")
public class EndpointController {
    private final static Logger log = LoggerFactory.getLogger(EndpointController.class);

    private final CreateEndpointUseCase createEndpointUseCase;
    private final ActivateEndpointUseCase activateEndpointUseCase;
    private final DeactivateEndpointUseCase deactivateEndpointUseCase;
    private final DeleteEndpointUseCase deleteEndpointUseCase;
    private final ChangeEndpointFrequencyUseCase changeEndpointFrequencyUseCase;
    private final EndpointQuery endpointQuery;

    @GetMapping
    ResponseEntity<List<EndpointDto>> findAll() {
        log.info("GET /endpoints");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        endpointQuery.findAll().stream()
                                .map(EndpointDto::new)
                                .collect(Collectors.toList())
                );
    }

    @PostMapping("/create")
    ResponseEntity<EndpointDto> createEndpoint(@RequestBody CreateEndpointCommand command) {
        var endpoint = createEndpointUseCase.createEndpoint(command);

        log.info("POST /endpoints/create");
        return endpoint
            .map(value -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new EndpointDto(value))
            ).orElse(ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build()
            );
    }

    @PatchMapping("/{uuid}/activate")
    ResponseEntity<EndpointDto> activateEndpoint(@PathVariable Endpoint.EndpointUUID uuid) {
        var endpoint = activateEndpointUseCase.activateEndpointUseCase(
                new ActivateEndpointCommand(uuid)
        );
        log.info("PATCH /endpoints/{}/activate", uuid);
        return optionalToOkOrNotFound(endpoint);

    }
    @PatchMapping("/{uuid}/deactivate")
    ResponseEntity<EndpointDto> deactivateEndpoint(@PathVariable Endpoint.EndpointUUID uuid) {
        var endpoint = deactivateEndpointUseCase.deactivateEndpoint(
                new DeactivateEndpointCommand(uuid)
        );
        log.info("PATCH /endpoints/{}/deactivate", uuid);
        return optionalToOkOrNotFound(endpoint);
    }

    @GetMapping("/{uuid}")
    ResponseEntity<EndpointDto> getEndpoint(@PathVariable Endpoint.EndpointUUID uuid) {
        return optionalToOkOrNotFound(endpointQuery.findEndpoint(uuid));
    }

    @DeleteMapping("/{uuid}")
    ResponseEntity<EndpointDto> deleteEndpoint(@PathVariable Endpoint.EndpointUUID uuid) {
        log.info("DELETE /endpoints/{}", uuid);
        return optionalToOkOrNotFound(deleteEndpointUseCase.deleteEndpoint(uuid));
    }

    @PatchMapping("/{uuid}/change-frequency")
    ResponseEntity<EndpointDto> editInterval(
            @PathVariable Endpoint.EndpointUUID uuid,
            @RequestBody ChangeFrequencyDto dto
    ) {
        var endpoint = changeEndpointFrequencyUseCase.changeFrequency(new ChangeEndpointFrequencyCommand(
                uuid, dto.timeInterval(), dto.timeUnit()
        ));

        return optionalToOkOrNotFound(endpoint);
    }


    private ResponseEntity<EndpointDto> optionalToOkOrNotFound(Optional<Endpoint> endpoint) {
        return endpoint.map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .body(new EndpointDto(value))
        ).orElse(ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build()
        );
    }


}
