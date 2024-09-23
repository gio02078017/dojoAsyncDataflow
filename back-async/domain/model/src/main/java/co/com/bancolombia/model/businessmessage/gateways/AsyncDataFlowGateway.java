package co.com.bancolombia.model.businessmessage.gateways;

import co.com.bancolombia.model.businessmessage.BusinessMessage;
import co.com.bancolombia.model.credentials.Credentials;
import reactor.core.publisher.Mono;

public interface AsyncDataFlowGateway {

    Mono<Credentials> generateCredentials(String user_identifier);

    Mono<Void> deliverMessage(String channelRef, BusinessMessage message);
}
