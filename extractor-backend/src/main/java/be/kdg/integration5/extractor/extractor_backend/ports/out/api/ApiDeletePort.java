package be.kdg.integration5.extractor.extractor_backend.ports.out.api;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;

public interface ApiDeletePort {
    void deleteApi(Api api);
}
