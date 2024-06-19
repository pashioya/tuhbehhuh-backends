package be.kdg.integration5.extractor.extractor_backend.seeding;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.ApiJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.ApiJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.EndpointJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointParameter;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
@Profile("local-dev")
public class DatabaseSeeder {

    private final ApiJpaEntityRepository apiJpaEntityRepository;

    private final EndpointJpaEntityRepository endpointJpaEntityRepository;

    @PostConstruct
    void createData() {
        try {
            var api1 = new Api(
                    UUID.fromString("7bff75e6-ce38-4618-8f94-dc8cf11745cb"),"api1",
                    "https://data.sensor.community"
            );
            var endpoint1 = new Endpoint(
                    UUID.fromString("017f12f5-8acb-4531-ab77-0e5208a31bca"),"endpoint1",
                    api1, "static/v2/data.json", new ArrayList<>(), true,
                    5, TimeUnit.MINUTES
            );

            apiJpaEntityRepository.save(new ApiJpaEntity(api1));
            endpointJpaEntityRepository.save(new EndpointJpaEntity(endpoint1));

            var api2 = new Api(
                    UUID.fromString("e56f8d8b-b085-4616-82b1-43a2faeafaf2"), "api2",
                    "https://telraam-api.net", "X-Api-Key", System.getenv("API_KEY_TELRAAM")
            );
            var endpoint2 = new Endpoint(
                    UUID.fromString("8c9a8f25-e54e-4884-aee6-a4529c5424ba"), "endpoint2",
                    api2, "v1/reports/traffic_snapshot_live", new ArrayList<>(), true,
                    5, TimeUnit.MINUTES
            );

            apiJpaEntityRepository.save(new ApiJpaEntity(api2));
            endpointJpaEntityRepository.save(new EndpointJpaEntity(endpoint2));

            var api3 = new Api(
                    UUID.fromString("85de26e8-8e2c-11ee-b9d1-0242ac120002"), "api3",
                    "https://api.opensensemap.org"
            );
            var endpoint3Uuid = UUID.fromString("2889936e-8e2d-11ee-b9d1-0242ac120002");

            var par = new EndpointParameter();
            par.setParentUuid(new Endpoint.EndpointUUID(endpoint3Uuid));
            par.setParameterKey("format");
            par.setParameterValue("json");

            var endpoint3 = new Endpoint(
                    endpoint3Uuid, "endpoint3",
                    api3, "boxes", List.of(par), true,
                    5, TimeUnit.MINUTES
            );

            apiJpaEntityRepository.save(new ApiJpaEntity(api3));
            endpointJpaEntityRepository.save(new EndpointJpaEntity(endpoint3));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
