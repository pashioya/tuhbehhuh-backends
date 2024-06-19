package be.kdg.integration5.extractor.extractor_backend.ports.out.endpointresponse;

import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;
import be.kdg.integration5.extractor.extractor_backend.domain.ResponseData;
import com.mysql.cj.x.protobuf.MysqlxCursor;

import java.net.http.HttpResponse;
import java.util.UUID;

public interface EndpointResponseCreatePort {
    void createResponse(ResponseData responseData);
}
