package be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public record ApiDto(
        UUID uuid,
        String name,
        String vendorUrl,
        Optional<String> apiKeyParameterName,
        Optional<String> apiKey,
        Optional<Integer> maxRequestsPerDay,

        List<EndpointDto> endpoints
) {

    public ApiDto(Api api, List<Endpoint> endpoints) {
        this(
                api.getUuid().uuid(),
                api.getName(),
                api.getVendorUrl().toString(),
                api.getApiKeyParameterName(),
                api.getApiKey(),
                api.getMaxRequestsPerDay(),
                endpoints.stream().map(EndpointDto::new).collect(Collectors.toList())
        );
    }
}
