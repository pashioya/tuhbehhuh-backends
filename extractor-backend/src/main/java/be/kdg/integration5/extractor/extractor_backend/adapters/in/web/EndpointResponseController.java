package be.kdg.integration5.extractor.extractor_backend.adapters.in.web;

import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses.EndpointResponseDto;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.EndpointResponseQuery;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/endpoint-responses")
public class EndpointResponseController {
    private final EndpointResponseQuery endpointResponseQuery;



    @GetMapping("/endpoint/{endpointUUID}")
    public ResponseEntity<List<EndpointResponseDto>> findAllByEndpointUUID(@PathVariable String endpointUUID) {
        try{
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        endpointResponseQuery.findAllByEndpointUUID(UUID.fromString(endpointUUID)).stream()
                                .map(EndpointResponseDto::new)
                                .collect(Collectors.toList())
                );}
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

    }
}
