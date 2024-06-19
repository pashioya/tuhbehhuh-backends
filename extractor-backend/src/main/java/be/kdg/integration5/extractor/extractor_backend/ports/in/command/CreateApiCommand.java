package be.kdg.integration5.extractor.extractor_backend.ports.in.command;

public record CreateApiCommand(
        String apiKey,
        String apiKeyParameterName,
         int maxRequestsPerDay,
        String name,
        String vendorUrl

) {
}
