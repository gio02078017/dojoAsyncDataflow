package co.com.bancolombia.usecase.business;

import co.com.bancolombia.model.businessmessage.BusinessMessage;
import co.com.bancolombia.model.businessmessage.Message;
import co.com.bancolombia.model.businessmessage.gateways.AsyncDataFlowGateway;
import co.com.bancolombia.model.credentials.Credentials;
import lombok.RequiredArgsConstructor;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@RequiredArgsConstructor
public class BusinessUseCase {
    private final AsyncDataFlowGateway asyncDataFlowGateway;

    public Mono<Credentials> generateCredentials(String user_ref) {
        return asyncDataFlowGateway.generateCredentials(user_ref);
    }

    public Mono<Object> asyncBusinessFlow(String delay, String channelRef) {
        return Mono.empty().doOnSuccess(serverResponse -> processAsyncIOBlocking(Integer.parseInt(delay), channelRef));
    }

    private Disposable processAsyncIOBlocking(int delay, String channelRef) {
        return Mono.fromRunnable(() -> doThisAsync(delay, channelRef).subscribe()).subscribeOn(Schedulers.boundedElastic()).subscribe();
    }

    private Mono<Void> doThisAsync(int delay, String channelRef) {
        try {
            Thread.sleep(delay);
            System.out.println("this process finish after "+ delay);
            BusinessMessage deliverMessage = BusinessMessage.builder()
                    .messageId(UUID.randomUUID().toString())
                    .CorrelationId(UUID.randomUUID().toString())
                    .messageData(Message.builder().code("100").title("process after "+ delay).detail("some detail " +UUID.randomUUID().toString()).severity("INFO").build())
                    .channelRef(channelRef)
                    .eventName("businessEvent")
                    .build();

            return asyncDataFlowGateway.deliverMessage(channelRef, deliverMessage);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Mono.empty();
    }
}
