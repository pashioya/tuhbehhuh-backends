package be.kdg.integration5.extractor.extractor_backend.core;

import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.domain.ResponseData;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse.EndpointResponseCreatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

public class RequestExecutor implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(RequestScheduler.class);

    private final List<EndpointResponseCreatePort> endpointResponseCreatePorts;
    private final Endpoint endpoint;

    public RequestExecutor(
            List<EndpointResponseCreatePort> endpointResponseCreatePorts,
            Endpoint endpoint
    ) {
        this.endpointResponseCreatePorts = endpointResponseCreatePorts;
        this.endpoint = endpoint;
    }

    @Override
    public void run() {
        var time = LocalDateTime.now();
        var response = this.endpoint.makeRequest();
        if (response.isEmpty()) {
            log.error(MessageFormat.format(
                    "Request returned by the endpoint was empty. Endpoint UUID: {0}",
                    this.endpoint.getUuid().uuid()
            ));
            return;
        }
        var responseData = new ResponseData(this.endpoint.getUuid(), response.get(), time);
        endpointResponseCreatePorts.forEach(p -> p.createResponse(responseData));

    }
}
