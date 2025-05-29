package gorae.backend.common.paypal;

import com.fasterxml.jackson.databind.ObjectMapper;
import gorae.backend.dto.paypal.PaypalAccessTokenDto;
import gorae.backend.dto.paypal.CreateOrderDto;
import gorae.backend.dto.paypal.CreateOrderRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static gorae.backend.constant.endpoint.Endpoint.createUrl;
import static gorae.backend.constant.endpoint.PaypalEndpoint.*;

@Slf4j
@Component
public class PaypalHttpClient {
    private final HttpClient httpClient;
    private final PaypalProperties paypalProperties;
    private final ObjectMapper objectMapper;

    private static final String BEARER_TYPE = "Bearer ";

    @Autowired
    public PaypalHttpClient(PaypalProperties paypalProperties, ObjectMapper objectMapper) {
        this.paypalProperties = paypalProperties;
        this.objectMapper = objectMapper;
        httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public PaypalAccessTokenDto getAccessToken() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalProperties.getBaseUrl(), GET_ACCESS_TOKEN)))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, encodeBasicCredentials())
                .header(HttpHeaders.ACCEPT_LANGUAGE, "en_US")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=client_credentials"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        log.info(content);
        return objectMapper.readValue(content, PaypalAccessTokenDto.class);
    }

    public CreateOrderDto createOrder(CreateOrderRequestDto orderRequestDto) throws Exception {
        PaypalAccessTokenDto paypalAccessTokenDto = getAccessToken();
        String payload = objectMapper.writeValueAsString(orderRequestDto);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalProperties.getBaseUrl(), ORDER_CHECKOUT)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + paypalAccessTokenDto.accessToken())
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        log.info(content);
        return objectMapper.readValue(content, CreateOrderDto.class);
    }

    private String encodeBasicCredentials() {
        String input = paypalProperties.getClientId() + ":" + paypalProperties.getSecret();
        return "Basic " + Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}
