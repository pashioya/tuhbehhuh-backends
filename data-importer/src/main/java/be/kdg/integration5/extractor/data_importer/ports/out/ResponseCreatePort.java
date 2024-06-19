package be.kdg.integration5.extractor.data_importer.ports.out;

import be.kdg.integration5.extractor.data_importer.domain.ResponseData;

public interface ResponseCreatePort {
    void createResponse(ResponseData responseData);
}
