package co.com.bancolombia.consumer.models;

import co.com.bancolombia.model.businessmessage.BusinessMessage;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DTOBusinessMessage {
    String channelRef;
    String messageId;
    String correlationId;
    BusinessMessage messageData;
    String eventName;
}
