package be.kdg.integration5.extractor.extractor_backend.config;

import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses.EndpointDto;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.ApiJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointParameterJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.adapters.out.db.entity.EndpointResponseJpaEntity;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.adapters.in.web.responses.EndpointParameterDto;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointParameter;
import be.kdg.integration5.extractor.extractor_backend.domain.EndpointResponse;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateApiCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.CreateEndpointCommand;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;



@Configuration
public class ExtractorBackendConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        Converter<CreateApiCommand, Api> createApiCommandApiConverter = new AbstractConverter<>() {
            @Override
            protected Api convert(CreateApiCommand source) {
                if (source == null)
                    return null;
                Api destination = new Api();
                destination.setUuid(new Api.ApiUUID(UUID.randomUUID()));
                destination.setName(source.name());
                try {
                    destination.setVendorUrl(new URL(source.vendorUrl()));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                destination.setApiKeyParameterName(Optional.ofNullable(source.apiKeyParameterName()));
                destination.setApiKey(Optional.ofNullable(source.apiKey()));
                destination.setMaxRequestsPerDay(Optional.of(source.maxRequestsPerDay()));
                return destination;
            }
        };

        Converter<ApiJpaEntity, Api> apiJpaEntityApiConverter = new AbstractConverter<>() {
            @Override
            protected Api convert(ApiJpaEntity source) {
                if (source == null)
                    return null;
                Api destination = new Api();
                destination.setUuid(new Api.ApiUUID(source.getUuid()));
                destination.setName(source.getName());
                try {
                    destination.setVendorUrl(new URL(source.getVendorUrl()));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                destination.setApiKeyParameterName(Optional.ofNullable(source.getApiKeyParameterName()));
                destination.setApiKey(Optional.ofNullable(source.getApiKey()));
                destination.setMaxRequestsPerDay(Optional.of(source.getMaxRequestsPerDay()));
                return destination;
            }
        };


    Converter<CreateEndpointCommand, Endpoint> createEndpointCommandEndpointConverter = new AbstractConverter<>() {
        @Override
        protected Endpoint convert(CreateEndpointCommand source) {
            if (source == null) {
                return null;
            }
//            INFO: Don't Forget to add the parameters to the endpoint
            Endpoint.EndpointUUID uuid = new Endpoint.EndpointUUID(UUID.randomUUID());
            Endpoint destination = new Endpoint();
            destination.setUuid(uuid);
            destination.setName(source.name());
            destination.setEndpointPath(source.endpointPath());
            destination.setActive(source.active());
            destination.setTimeInterval(source.timeInterval());
            destination.setTimeUnit(source.timeUnit());

            return destination;
        }
    };

    Converter< EndpointJpaEntity, Endpoint> endpointJpaEntityEndpointConverter = new AbstractConverter<>() {
        @Override
        protected Endpoint convert(EndpointJpaEntity source) {
            if (source == null) {
                return null;
            }
            Endpoint.EndpointUUID uuid = new Endpoint.EndpointUUID(source.getUuid());
            Endpoint destination = new Endpoint();
            destination.setUuid(uuid);
            destination.setName(source.getName());
            destination.setEndpointPath(source.getEndpointPath());
            destination.setActive(source.isActive());
            destination.setTimeInterval(source.getTimeInterval());
            destination.setTimeUnit(source.getTimeUnit());

            return destination;
        }

    };

    Converter<EndpointParameterJpaEntity, EndpointParameter> endpointParameterJpaEntityEndpointParameterConverter = new AbstractConverter<>() {
        @Override
        protected EndpointParameter convert(EndpointParameterJpaEntity source) {
            if (source == null) {
                return null;
            }
            EndpointParameter destination = new EndpointParameter();
            destination.setUuid(new EndpointParameter.EndpointParameterUUID(source.getUuid()));
            destination.setParentUuid(new Endpoint.EndpointUUID(source.getEndpointUuid()));
            destination.setParameterKey(source.getParameterKey());
            destination.setParameterValue(source.getParameterValue());
            return destination;
        }
    };

    Converter<CreateEndpointCommand.EndpointParameter, EndpointParameter> createEndpointCommandEndpointParameterConverter = new AbstractConverter<>() {
        @Override
        protected EndpointParameter convert(CreateEndpointCommand.EndpointParameter source) {
            if (source == null) {
                return null;
            }
            EndpointParameter destination = new EndpointParameter();
            destination.setUuid(new EndpointParameter.EndpointParameterUUID(UUID.randomUUID()));
            destination.setParameterKey(source.parameterKey());
            destination.setParameterValue(source.parameterVal());
            return destination;
        }
    };

    Converter<EndpointResponseJpaEntity, EndpointResponse> endpointResponseJpaEntityEndpointResponseConverter = new AbstractConverter<>() {
        @Override
        protected EndpointResponse convert(EndpointResponseJpaEntity source) {
            if (source == null) {
                return null;
            }
            EndpointResponse destination = new EndpointResponse();
            destination.setUuid(new EndpointResponse.EndpointResponseUUID(source.getUuid()));
            destination.setEndpointUUID(new Endpoint.EndpointUUID(source.getEndpointUUID()));
            destination.setTimeSent(source.getTimeReceived());
            destination.setBody(Optional.ofNullable(source.getBody()));
            destination.setRequest(source.getRequest());
            destination.setStatusCode(source.getStatusCode());
            try {
                destination.setAnsweringUri(new URI(source.getAnsweringUri()));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            return destination;
        }
    };

    Converter<Endpoint, EndpointDto> endpointEndpointDtoConverter = new AbstractConverter<>() {
        @Override
        protected EndpointDto convert(Endpoint source) {
            if (source == null) {
                return null;
            }
            return new EndpointDto(
                    source.getUuid().uuid(),
                    source.getName(),
                    source.getApi().getUuid().uuid(),
                    source.getEndpointPath(),
                    source.getParameters().stream().map(EndpointParameterDto::new).collect(Collectors.toList()),
                    source.isActive(),
                    source.getTimeInterval(),
                    source.getTimeUnit()
            );
        }
    };

        modelMapper.addConverter(endpointEndpointDtoConverter);
        modelMapper.addConverter(endpointResponseJpaEntityEndpointResponseConverter);
        modelMapper.addConverter(createEndpointCommandEndpointParameterConverter);
        modelMapper.addConverter(endpointParameterJpaEntityEndpointParameterConverter);
        modelMapper.addConverter(endpointJpaEntityEndpointConverter);
        modelMapper.addConverter(createEndpointCommandEndpointConverter);
        modelMapper.addConverter(createApiCommandApiConverter);
        modelMapper.addConverter(apiJpaEntityApiConverter);
        return modelMapper;
    }
}
