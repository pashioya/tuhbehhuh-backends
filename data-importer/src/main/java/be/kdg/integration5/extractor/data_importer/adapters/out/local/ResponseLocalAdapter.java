package be.kdg.integration5.extractor.data_importer.adapters.out.local;

import be.kdg.integration5.extractor.data_importer.adapters.out.datalake.ResponseDataLakeAdapter;
import be.kdg.integration5.extractor.data_importer.domain.ResponseData;
import be.kdg.integration5.extractor.data_importer.ports.out.ResponseCreatePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@AllArgsConstructor
@Service
@Profile("local-dev")
public class ResponseLocalAdapter implements ResponseCreatePort {

    private final static Logger log = LoggerFactory.getLogger(ResponseDataLakeAdapter.class);
    private final ObjectMapper objectMapper;


    @Override
    public void createResponse(ResponseData responseData) {
        try {
            log.debug("saving file");
            var data = objectMapper.writeValueAsString(responseData);
            var file = new File("./local-data/" + responseData.endpointUUID().toString());
            if (!file.exists()) file.mkdirs();
            var stream = new FileOutputStream("./local-data/" + responseData.endpointUUID().toString() + "/" + responseData.timeSent().toString() + ".json");
            stream.write(data.getBytes());
            stream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
