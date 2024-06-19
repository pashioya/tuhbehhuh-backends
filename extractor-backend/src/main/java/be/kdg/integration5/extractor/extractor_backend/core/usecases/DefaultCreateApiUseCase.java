package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateApiCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.CreateApiUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.api.ApiCreatePort;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DefaultCreateApiUseCase implements CreateApiUseCase {
    private final List<ApiCreatePort> apiCreatePortList;
    private final static Logger log = LoggerFactory.getLogger(Endpoint.class);
    private ModelMapper modelMapper;

    @Override
    public Optional<Api> createApi(CreateApiCommand command) {
        var api = modelMapper.map(command, Api.class);
        apiCreatePortList.forEach(p -> p.createApi(api));
        return Optional.of(api);
    }
}
