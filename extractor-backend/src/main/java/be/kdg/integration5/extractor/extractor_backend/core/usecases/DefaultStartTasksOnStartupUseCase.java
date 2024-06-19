package be.kdg.integration5.extractor.extractor_backend.core.usecases;

import be.kdg.integration5.extractor.extractor_backend.ports.in.usecase.StartTasksOnStartupUseCase;
import be.kdg.integration5.extractor.extractor_backend.ports.out.StartUpRequestSchedulesPort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import java.text.MessageFormat;

@AllArgsConstructor
@Service
public class DefaultStartTasksOnStartupUseCase implements StartTasksOnStartupUseCase {
    private final static Logger log = LoggerFactory.getLogger(DefaultStartTasksOnStartupUseCase.class);

    private final EndpointLoadPort endpointLoadPort;
    private final StartUpRequestSchedulesPort startUpRequestSchedulesPort;

    @EventListener(ApplicationReadyEvent.class)
    @Override
    public void startTasks() {
        //TODO make the initial delays somehow smarter, maybe start on the hour
        var endpoints = endpointLoadPort.loadAllActiveEndpoints();
        log.info(MessageFormat.format(
                "Starting {0} tasks as the startup procedure.",
                endpoints.size()
        ));
        startUpRequestSchedulesPort.startUpRequestSchedules(endpoints);
    }
}