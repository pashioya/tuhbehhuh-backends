package be.kdg.integration5.extractor.extractor_backend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
public class Endpoint {

    private final static Logger log = LoggerFactory.getLogger(Endpoint.class);


    private EndpointUUID uuid;
    private String name;
    private Api api;
    private String endpointPath;
    private List<EndpointParameter> parameters;
    private boolean active;
    private int timeInterval;
    private TimeUnit timeUnit;

    public record EndpointUUID(UUID uuid) {
        public EndpointUUID(String strUuid) {
            this(UUID.fromString(strUuid));
        }
    }

    public void changeFrequency(int timeInterval, TimeUnit timeUnit) {
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isInactive() {
        return !this.active;
    }

 public Optional<HttpResponse<String>> makeRequest() {
    log.debug("In the run function of {}", this.uuid);
    HttpRequest request;
    HttpResponse<String> response;


    try {
        request = this.getFullRequest();
        log.debug("Running the task for the endpoint {}", request.uri());
        response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    } catch (URISyntaxException | IOException | InterruptedException e) {
        log.error("An Exception {} was thrown when making a request!", e.getClass().getName());
        log.error("Exception details: {}", e.getMessage());
        log.debug("Exception stack trace:", e);
        return Optional.empty();
    }

    if (response != null) {
        log.info("Request to {} has returned code {}", request.uri(), response.statusCode());
        return Optional.of(response);
    } else {
        log.info("The request for url {} failed; the response is null.", request.uri());
        return Optional.empty();
    }
}
    public HttpRequest getFullRequest() throws URISyntaxException {
        var url = UriComponentsBuilder.fromUri(api.getVendorUrl().toURI());
        url.path(this.getEndpointPath());
        if (!parameters.isEmpty()) {
            log.debug("adding params");
            for (var parameter : this.parameters) {
                log.debug("adding parameter");
                url.queryParam(parameter.getParameterKey(), parameter.getParameterValue());
            }
        }
        var requestBuilder = HttpRequest.newBuilder(url.build().toUri());

        if (this.api.getApiKey().isPresent() && this.api.getApiKeyParameterName().isPresent()) {
            requestBuilder.header(this.api.getApiKeyParameterName().get(), this.api.getApiKey().get());
        }

        return requestBuilder.build();
    }


    public Endpoint(
            UUID uuid,
            String name,
            Api api,
            String endpointPath,
            List<EndpointParameter> parameters,
            boolean active,
            int timeInterval,
            TimeUnit timeUnit) {
        this.uuid = new EndpointUUID(uuid);
        this.name = name;
        this.api = api;
        this.endpointPath = endpointPath;
        this.parameters = parameters;
        this.active = active;
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
    }

    public Endpoint(
            String name,
            Api api,
            String endpointPath,
            List<EndpointParameter> parameters,
            boolean active,
            int timeInterval,
            TimeUnit timeUnit) {
        this(
                UUID.randomUUID(),
                name,
                api,
                endpointPath,
                parameters,
                active,
                timeInterval,
                timeUnit);
    }
}
