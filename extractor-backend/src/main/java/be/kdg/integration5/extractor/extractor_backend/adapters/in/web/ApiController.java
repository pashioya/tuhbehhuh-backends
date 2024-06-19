package be.kdg.integration5.extractor.extractor_backend.adapters.in.web;

import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses.ApiDto;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateApiCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.ApiQuery;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.EndpointQuery;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.CreateApiUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.DeleteApiUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/apis")
public class ApiController {

    private final static Logger log = LoggerFactory.getLogger(ApiController.class);
    private final CreateApiUseCase createApiUseCase;
    private final DeleteApiUseCase deleteApiUseCase;
    private final ApiQuery apiQuery;
    private final EndpointQuery endpointQuery;

    @GetMapping
    ResponseEntity<List<ApiDto>> getApis() {
        var endpoints = endpointQuery.findAll();
        var query = apiQuery.findAll().stream().map(a -> {
            var relevantEndpoints = endpoints.stream().filter(e -> e.getApi().getUuid().equals(a.getUuid())).toList();
            return new ApiDto(a, relevantEndpoints);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(query);
    }

    @PostMapping("/create")
    ResponseEntity<ApiDto> createApi(@RequestBody CreateApiCommand command) {
        log.info("POST /apis/create");
        var api = createApiUseCase.createApi(command);
        return api.map(value -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiDto(api.get(), new ArrayList<>()))).orElse(ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
    }

    @GetMapping("/{uuid}")
    ResponseEntity<ApiDto> getApi(@PathVariable Api.ApiUUID uuid) {
        var possibleApi = apiQuery.findById(uuid);
        if (possibleApi.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        var endpoints = endpointQuery.findAllEndpointsForApi(uuid);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiDto(
                        possibleApi.get(),
                        endpoints));
    }

    @DeleteMapping("/{uuid}")
    ResponseEntity<ApiDto> deleteApi(@PathVariable Api.ApiUUID uuid) {
        var api = deleteApiUseCase.deleteApi(uuid);
        return api.map(value -> ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiDto(api.get(), new ArrayList<>()))).orElse(ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

}
