package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;

import java.util.Optional;

public interface DeleteApiUseCase {

    Optional<Api> deleteApi(Api.ApiUUID apiUUID);

}
