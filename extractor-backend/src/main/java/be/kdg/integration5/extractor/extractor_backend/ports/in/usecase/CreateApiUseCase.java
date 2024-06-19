package be.kdg.integration5.extractor.extractor_backend.ports.in.usecase;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateApiCommand;

import java.util.Optional;

public interface CreateApiUseCase {

    Optional<Api> createApi(CreateApiCommand command);
}
