package co.com.bancolombia.model.businessmessage;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BusinessMessage {
    private final String channelRef;
    private final String messageId;
    private final String CorrelationId;
    private final Message messageData;
    private final String eventName;
}
