package co.com.bancolombia.consumer;

import co.com.bancolombia.consumer.models.DTOBusinessMessage;
import co.com.bancolombia.consumer.models.DTORequestCredentials;
import co.com.bancolombia.consumer.models.DTOResponseCredentials;
import co.com.bancolombia.model.businessmessage.BusinessMessage;
import co.com.bancolombia.model.businessmessage.gateways.AsyncDataFlowGateway;
import co.com.bancolombia.model.credentials.Credentials;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class RestConsumer implements AsyncDataFlowGateway /* implements Gateway from domain */{

    @Value("${spring.application.name}")
    public String applicationRef;

    private final WebClient client;


    // these methods are an example that illustrates the implementation of WebClient.
    // You should use the methods that you implement from the Gateway from the domain.
    @CircuitBreaker(name = "testGet" /*, fallbackMethod = "testGetOk"*/)
    public Mono<ObjectResponse> testGet() {
        return client
                .get()
                .retrieve()
                .bodyToMono(ObjectResponse.class);
    }

    @Override
    public Mono<Credentials> generateCredentials(String userIdentifier) {
        ObjectRequest request = ObjectRequest.builder()
                .application_ref(applicationRef)
                .user_ref(userIdentifier)
                .build();

        return client
                .post().uri("/ext/channel/create")
                .body(Mono.just(request), DTORequestCredentials.class)
                .retrieve()
                .bodyToMono(DTOResponseCredentials.class)
                .map(this::mapperToCredentials);
    }

    @Override
    public Mono<Void> deliverMessage(String channelRef, BusinessMessage message) {
        return client
                .post().uri("/ext/channel/deliver_message")
                .bodyValue(mapperDTO(message))
                .retrieve().toBodilessEntity().then();
    }

    private DTOBusinessMessage mapperDTO(BusinessMessage message) {
        return DTOBusinessMessage.builder()
                .channelRef(message.getChannelRef())
                .correlationId(message.getCorrelationId())
                .eventName(message.getEventName())
                .messageId(message.getMessageId())
                .messageData(message)
                .build();
    }

    private Credentials mapperToCredentials(DTOResponseCredentials dtoCredentials) {
        return Credentials.builder().channelRef(dtoCredentials.getChannelRef()).channelSecret(dtoCredentials.getChannelSecret()).build();
    }
}
