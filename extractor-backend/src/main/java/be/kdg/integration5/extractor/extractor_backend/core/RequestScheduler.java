package be.kdg.integration5.extractor.extractor_backend.core;

import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.out.StartUpRequestSchedulesPort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

@AllArgsConstructor
@Service
public class RequestScheduler implements
        EndpointCreatePort, EndpointDeactivatePort, EndpointActivatePort,
        StartUpRequestSchedulesPort, EndpointDeletePort, EndpointChangeFrequencyPort {
    private final static Logger log = LoggerFactory.getLogger(RequestScheduler.class);
    private final EndpointLoadPort endpointLoadPort;
    private final ScheduledExecutorService executorService;
    private final RequestExecutorFactory requestExecutorFactory;
    private final Map<Endpoint.EndpointUUID, ScheduledFuture<?>> tasks;


    @Override
    public void createEndpoint(Endpoint endPoint) {
        if (endPoint.isActive()) {
            log.info(MessageFormat.format("Adding new task for endpoint: {0}.", endPoint));
            addTask(endPoint);
        }
    }

    @Override
    public void deactivatePort(Endpoint endpoint) {
        checkAndRemoveTask(endpoint.getUuid());
    }

    @Override
    public void activateEndpoint(Endpoint endpoint) {
        this.addTask(endpoint);
    }

    @Override
    public void startUpRequestSchedules(List<Endpoint> endpointList) {
        endpointList.forEach(this::addTask);
    }

    private void addTask(Endpoint endPoint) {
        checkAndRemoveTask(endPoint.getUuid());

        if (endPoint.isInactive()) {
            log.info(MessageFormat.format(
                    "Add Task was called but the passed Endpoint is inactive. Aborting the action. UUID: {0}",
                    endPoint.getUuid()
            ));
            return;
        }

        log.info(MessageFormat.format(
                "Adding new task for endpoint: {0} with a interval of {1} {2}",
                endPoint, endPoint.getTimeInterval(), endPoint.getTimeUnit()
        ));
        var executor = requestExecutorFactory.getExecutor(endPoint);
        //TODO maybe make the initial delay somehow smarter?
        var task = executorService.scheduleAtFixedRate(
                executor,
                0,
                endPoint.getTimeInterval(),
                endPoint.getTimeUnit()
        );
        tasks.put(endPoint.getUuid(), task);
    }

    @Override
    public void changeFrequency(Endpoint endpoint) {
        addTask(endpoint);
    }

    @Override
    public void deleteEndpoint(Endpoint endpoint) {
        checkAndRemoveTask(endpoint.getUuid());
    }

    @Override
    public void deleteByApi(Api api) {
        var endpoints = endpointLoadPort.loadAllEndpointsForApi(api.getUuid());
        endpoints.forEach(e -> checkAndRemoveTask(e.getUuid()));
    }


    private void checkAndRemoveTask(Endpoint.EndpointUUID endpointUUID) {
        var existing = tasks.get(endpointUUID);
        if (existing != null) {
            existing.cancel(true);
            tasks.remove(endpointUUID);
        }
    }
}
