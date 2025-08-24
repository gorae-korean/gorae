package gorae.backend.dto.client.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.Map;

public record PaypalWebhookDto(
        String id,
        @JsonProperty("resource_type") String resourceType,
        @JsonProperty("event_type") String eventType,
        @JsonProperty("create_time") Instant createTime,
        String summary,
        Map<String, Object> resource
) {
}
