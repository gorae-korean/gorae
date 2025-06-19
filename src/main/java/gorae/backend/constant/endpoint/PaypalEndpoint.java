package gorae.backend.constant.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaypalEndpoint implements Endpoint {
    GET_ACCESS_TOKEN("/v1/oauth2/token"),
    GET_CLIENT_TOKEN("/v1/identity/generate-token"),
    ORDER_CHECKOUT("/v2/checkout/orders");

    private final String path;
}
