package be.kdg.integration5.extractor.extractor_backend.adapters.out.db;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.ApiJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointParameterJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.ApiJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.EndpointJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.EndpointParameterJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointParameter;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class EndpointDBAdapter implements
        EndpointLoadPort, EndpointCreatePort, EndpointDeactivatePort,
        EndpointActivatePort, EndpointDeletePort, EndpointChangeFrequencyPort {

    private final ApiJpaEntityRepository apiRepository;
    private final EndpointJpaEntityRepository endpointRepository;
    private final EndpointParameterJpaEntityRepository endpointParameterRepository;
    private final ModelMapper modelMapper;


    @Override
    public Optional<Endpoint> loadEndpoint(Endpoint.EndpointUUID uuid) {
        var endpoint = endpointRepository.findById(uuid.uuid());
        if (endpoint.isEmpty()) return Optional.empty();

        var parameters = endpointParameterRepository.findByEndpointUuid(uuid.uuid());
        var api = apiRepository.findByEndpointUuid(uuid.uuid());

        Endpoint endPoint = modelMapper.map(endpoint.get(), Endpoint.class);
        api.ifPresent(apiJpaEntity -> endPoint.setApi(modelMapper.map(apiJpaEntity, Api.class)));
        endPoint.setParameters(parameters.stream().map(p -> modelMapper.map(p, EndpointParameter.class)).toList());
        return Optional.of(endPoint);
    }

    @Override
    public List<Endpoint> loadAllEndpointsForApi(Api.ApiUUID apiUUID) {

        var optionalApi = apiRepository.findById(apiUUID.uuid());
        var apis = new ArrayList<ApiJpaEntity>();
        if (optionalApi.isEmpty()) {
            return new ArrayList<>();
        } else {
            apis.add(optionalApi.get());
        }
        var endpoints = endpointRepository.findByApi(apiUUID.uuid());
        var parameters = endpointParameterRepository.findByInEndpointUuids(
                endpoints.stream().map(EndpointJpaEntity::getUuid).collect(Collectors.toList())
        );
        return createEndpointsFromParts(
                parameters, apis, endpoints
        );
    }

    @Override
    public List<Endpoint> loadAllActiveEndpoints() {
        return createEndpointsFromParts(
                endpointParameterRepository.findByActiveEndpoints(),
                apiRepository.findByActiveEndpoints(),
                endpointRepository.findByActive()
        );
    }

    @Override
    public List<Endpoint> loadAllEndpoints() {
        return createEndpointsFromParts(
            endpointParameterRepository.findAll(),
            apiRepository.findAll(),
            endpointRepository.findAll()
        );
    }


    private List<Endpoint> createEndpointsFromParts(
        List<EndpointParameterJpaEntity> parameters,
        List<ApiJpaEntity> apis,
        List<EndpointJpaEntity> endpoints
    ) {
        Map<Endpoint.EndpointUUID, List<EndpointParameterJpaEntity>> parameterMap = new HashMap<>();

        parameters.forEach(p -> parameterMap
                .computeIfAbsent(new Endpoint.EndpointUUID(p.getUuid()), k -> new ArrayList<>())
                .add(p)
        );

        var apisMap = apis.stream().collect(Collectors.toMap(ApiJpaEntity::getUuid, Function.identity()));

        return endpoints.stream().map(e -> {
            var api = apisMap.get(e.getApiUuid());
            var curParameters = parameterMap.get(new Endpoint.EndpointUUID(e.getUuid()));
            if (api == null) {
                return null;
            }
            //FIXME make this smarter so that it does not crash the program
            Endpoint endpoint = modelMapper.map(e, Endpoint.class);
            endpoint.setApi(modelMapper.map(api, Api.class));
            if (curParameters != null) {
                endpoint.setParameters(curParameters.stream().map(p -> modelMapper.map(p, EndpointParameter.class)).toList());
            } else {
                endpoint.setParameters(new ArrayList<>());
            }
            return endpoint;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void createEndpoint(Endpoint endPoint) {
        endpointRepository.save(new EndpointJpaEntity(endPoint));
    }

    @Override
    public void deactivatePort(Endpoint endpoint) {
        endpointRepository.save(new EndpointJpaEntity(endpoint));
    }

    @Override
    public void activateEndpoint(Endpoint endpoint) {
        endpointRepository.save(new EndpointJpaEntity(endpoint));
    }

    @Override
    public void deleteEndpoint(Endpoint endpoint) {
        endpointParameterRepository.deleteByEndpoint(endpoint.getUuid().uuid());
        endpointRepository.delete(new EndpointJpaEntity(endpoint));
    }

    @Override
    public void deleteByApi(Api api) {
        endpointParameterRepository.deleteByApi(api.getUuid().uuid());
        endpointRepository.deleteByApi(api.getUuid().uuid());
    }

    @Override
    public void changeFrequency(Endpoint endpoint) {
        endpointRepository.save(new EndpointJpaEntity(endpoint));
    }
}
