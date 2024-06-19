package be.kdg.integration5.extractor.core;

import be.kdg.integration5.extractor.extractor_backend.core.usecases.DefaultChangeEndpointFrequencyUseCase;
import be.kdg.integration5.extractor.extractor_backend.domain.Api;
import be.kdg.integration5.extractor.extractor_backend.domain.Endpoint;
import be.kdg.integration5.extractor.extractor_backend.ports.in.command.ChangeEndpointFrequencyCommand;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointChangeFrequencyPort;
import be.kdg.integration5.extractor.extractor_backend.ports.out.endpoint.EndpointLoadPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultChangeEndpointFrequencyUseCaseTest {

    @Mock
    private EndpointLoadPort endpointLoadPort;

    @Mock
    private EndpointChangeFrequencyPort endpointChangeFrequencyPort1;

    @Mock
    private EndpointChangeFrequencyPort endpointChangeFrequencyPort2;

    @Captor
    private ArgumentCaptor<Endpoint> endpointCaptor;

    @Test
    void changeFrequency_Successful() throws MalformedURLException {
        // Given
        Endpoint.EndpointUUID endpointUUID = new Endpoint.EndpointUUID(UUID.randomUUID());
        ChangeEndpointFrequencyCommand command = new ChangeEndpointFrequencyCommand(endpointUUID, 10, TimeUnit.MINUTES);

        Api api = new Api(UUID.randomUUID(), "api1", "https://example.com");
        Endpoint originalEndpoint = new Endpoint(UUID.randomUUID(), "testEndpoint", api, "path", List.of(), true, 60, TimeUnit.SECONDS);

        when(endpointLoadPort.loadEndpoint(endpointUUID)).thenReturn(Optional.of(originalEndpoint));

        DefaultChangeEndpointFrequencyUseCase useCase = new DefaultChangeEndpointFrequencyUseCase(
                endpointLoadPort,
                List.of(endpointChangeFrequencyPort1, endpointChangeFrequencyPort2)
        );

        // When
        Optional<Endpoint> result = useCase.changeFrequency(command);

        // Then
        assertTrue(result.isPresent());
        assertEquals(10, result.get().getTimeInterval());
        assertEquals(TimeUnit.MINUTES, result.get().getTimeUnit());

//        verify(endpointLoadPort).loadEndpoint(endpointUUID);
        verify(endpointChangeFrequencyPort1).changeFrequency(endpointCaptor.capture());
        verify(endpointChangeFrequencyPort2).changeFrequency(endpointCaptor.capture());

        List<Endpoint> capturedEndpoints = endpointCaptor.getAllValues();
        assertEquals(2, capturedEndpoints.size());
        assertEquals(originalEndpoint, capturedEndpoints.get(0));
        assertEquals(result.get(), capturedEndpoints.get(1));
    }

    @Test
    void changeFrequency_EndpointNotFound() {
        // Given
        Endpoint.EndpointUUID endpointUUID = new Endpoint.EndpointUUID(UUID.randomUUID());
        ChangeEndpointFrequencyCommand command = new ChangeEndpointFrequencyCommand(endpointUUID, 10, TimeUnit.MINUTES);

        when(endpointLoadPort.loadEndpoint(endpointUUID)).thenReturn(Optional.empty());

        DefaultChangeEndpointFrequencyUseCase useCase = new DefaultChangeEndpointFrequencyUseCase(
                endpointLoadPort,
                List.of(endpointChangeFrequencyPort1, endpointChangeFrequencyPort2)
        );

        // When
        Optional<Endpoint> result = useCase.changeFrequency(command);

        // Then
        assertTrue(result.isEmpty());
        verify(endpointLoadPort).loadEndpoint(endpointUUID);
        verifyNoInteractions(endpointChangeFrequencyPort1, endpointChangeFrequencyPort2);
    }
}
