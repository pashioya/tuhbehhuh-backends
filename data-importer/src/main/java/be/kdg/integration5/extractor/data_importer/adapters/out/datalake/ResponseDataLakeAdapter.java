package be.kdg.integration5.extractor.data_importer.adapters.out.datalake;

import be.kdg.integration5.extractor.data_importer.adapters.config.AzureServiceBusModuleTopology;
import be.kdg.integration5.extractor.data_importer.domain.ResponseData;
import be.kdg.integration5.extractor.data_importer.ports.out.ResponseCreatePort;
import com.azure.storage.blob.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.format.datetime.standard.DateTimeFormatterFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

@AllArgsConstructor
@Service
@Profile({"remote-dev", "prod"})
public class ResponseDataLakeAdapter implements ResponseCreatePort {
    private final static Logger log = LoggerFactory.getLogger(ResponseDataLakeAdapter.class);
    private final ObjectMapper objectMapper;

    private final JmsTemplate jmsTemplate;

    @Override
    public void createResponse(ResponseData responseData) {
        log.debug("running the dl adapter");
        var contName = MessageFormat.format(
                "json/{0}",
                responseData.endpointUUID()
        );
        var container = new BlobContainerClientBuilder()
                .connectionString(System.getenv("AZURE_STORAGE_CONNECTION_STRING"))
                .containerName(contName)
                .buildClient();

        var fileName = MessageFormat.format(
                "{0}-{1}.json",
                responseData.endpointUUID(),
                responseData.timeSent().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm"))
        );

        BlobClient blob = container.getBlobClient(fileName);

        try {
            var bytes = objectMapper.writeValueAsString(responseData).getBytes("UTF8");
            var stream = new ByteArrayInputStream(bytes);
            blob.upload(stream);

            jmsTemplate.convertAndSend(
                    AzureServiceBusModuleTopology.API_DATA_TO_ETL,
                    MessageFormat.format(
                        "{0}/{1}",
                        responseData.endpointUUID(), fileName
                    )
            );
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


    }
}
