package co.com.bancolombia.model.businessmessage;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class Message {
    private String code;
    private String title;
    private String detail;
    private String severity;

}
