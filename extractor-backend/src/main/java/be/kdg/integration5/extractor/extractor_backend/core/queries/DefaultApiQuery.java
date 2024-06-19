package be.kdg.integration5.extractor.extractor_backend.core.queries;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.ports.in.query.ApiQuery;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiLoadPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultApiQuery implements ApiQuery {
    private final ApiLoadPort apiLoadPort;

    @Override
    public Optional<Api> findById(Api.ApiUUID uuid) {
        return apiLoadPort.findById(uuid);
    }

    @Override
    public List<Api> findAll() {
        return apiLoadPort.findAll();
    }
}
