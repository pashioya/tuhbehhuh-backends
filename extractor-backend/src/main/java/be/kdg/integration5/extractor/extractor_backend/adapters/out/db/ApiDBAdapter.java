package be.kdg.integration5.extractor.extractor_backend.adapters.out.db;

import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.ApiJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.repository.ApiJpaEntityRepository;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiCreatePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiDeletePort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiLoadPort;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class ApiDBAdapter implements
        ApiCreatePort, ApiLoadPort, ApiDeletePort {
    private final static Logger log = LoggerFactory.getLogger(ApiDBAdapter.class);

    private final ApiJpaEntityRepository apiRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createApi(Api api) {
        apiRepository.save(new ApiJpaEntity(api));
    }

    @Override
    public List<Api> findAll() {
        return apiRepository.findAll().stream().map(aJpa -> modelMapper.map(aJpa, Api.class)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Optional<Api> findById(Api.ApiUUID apiUUID) {
        return apiRepository.findById(apiUUID.uuid()).map(a -> modelMapper.map(a, Api.class));
    }

    @Override
    public void deleteApi(Api api) {
        apiRepository.delete(new ApiJpaEntity(api));
    }
}
