package be.kdg.integration5.extractor.extractor_backend.ports.in.query;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;

import java.util.List;
import java.util.Optional;

public interface ApiQuery {

    Optional<Api> findById(Api.ApiUUID uuid);
    List<Api> findAll();
}
