package co.com.bancolombia.model.credentials;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Credentials {
    private final String channelRef;
    private final String channelSecret;
}
