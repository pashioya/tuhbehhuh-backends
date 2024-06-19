package be.kdg.integration5.extractor.extractor_backend.ports.out.api;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

public interface ApiLoadPort {

    List<Api> findAll();

    Optional<Api> findById(Api.ApiUUID apiUUID);
}
