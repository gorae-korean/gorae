package gorae.backend.common.paypal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "paypal")
public class PaypalProperties {
    private String baseUrl;
    private String clientId;
    private String secret;
}
