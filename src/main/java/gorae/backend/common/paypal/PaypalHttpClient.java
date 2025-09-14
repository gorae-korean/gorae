package gorae.backend.common.paypal;

import com.fasterxml.jackson.databind.ObjectMapper;
import gorae.backend.dto.client.paypal.CreateOrderRequestDto;
import gorae.backend.dto.client.paypal.OrderDto;
import gorae.backend.dto.client.paypal.PaypalAccessTokenDto;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
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
import java.util.UUID;

import static gorae.backend.constant.endpoint.Endpoint.createUrl;
import static gorae.backend.constant.endpoint.PaypalEndpoint.GET_ACCESS_TOKEN;
import static gorae.backend.constant.endpoint.PaypalEndpoint.ORDER_CHECKOUT;

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
        log.info("[Paypal] GetAccessToken started");
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
        log.debug(content);

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            log.info("[Paypal] GetAccessToken succeeded");
        } else {
            log.error("[Paypal] GetAccessToken failed for code: {}", statusCode);
            throw new CustomException(ErrorStatus.PAYPAL_API_ERROR);
        }

        return objectMapper.readValue(content, PaypalAccessTokenDto.class);
    }

    public OrderDto createOrder(UUID memberId, CreateOrderRequestDto orderRequestDto) throws Exception {
        PaypalAccessTokenDto paypalAccessTokenDto = getAccessToken();
        String payload = objectMapper.writeValueAsString(orderRequestDto);

        log.info("[Paypal] CreateOrder started for member: {}", memberId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalProperties.getBaseUrl(), ORDER_CHECKOUT)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + paypalAccessTokenDto.accessToken())
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        log.info(content);

        int statusCode = response.statusCode();
        if (statusCode == 200 || statusCode == 201) {
            log.info("[Paypal] CreateOrder succeeded for member: {}", memberId);
        } else {
            log.error("[Paypal] CreateOrder failed for member: {}, code: {}", memberId, statusCode);
            throw new CustomException(ErrorStatus.PAYPAL_API_ERROR);
        }

        return objectMapper.readValue(content, OrderDto.class);
    }

    public OrderDto getOrder(UUID memberId, String orderId) throws Exception {
        PaypalAccessTokenDto paypalAccessTokenDto = getAccessToken();
        log.info("[Paypal] GetOrder started for member: {}, order: {}", memberId, orderId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalProperties.getBaseUrl(), ORDER_CHECKOUT) + "/" + orderId))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + paypalAccessTokenDto.accessToken())
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        log.info(content);

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            log.info("[Paypal] GetOrder succeeded for member: {} order: {}", memberId, orderId);
        } else {
            log.error("[Paypal] GetOrder failed for member: {}, order: {}, code: {}", memberId, orderId, statusCode);
            throw new CustomException(ErrorStatus.PAYPAL_API_ERROR);
        }

        return objectMapper.readValue(content, OrderDto.class);
    }

    public OrderDto captureOrder(UUID memberId, String orderId) throws Exception {
        PaypalAccessTokenDto paypalAccessTokenDto = getAccessToken();
        log.info("[Paypal] CaptureOrder started for order: {}", orderId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(paypalProperties.getBaseUrl(), ORDER_CHECKOUT) + "/" + orderId + "/capture"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER_TYPE + paypalAccessTokenDto.accessToken())
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        log.info(content);

        int statusCode = response.statusCode();
        if (statusCode == 200 || statusCode == 201) {
            log.info("[Paypal] CaptureOrder succeeded for member: {} order: {}", memberId, orderId);
        } else {
            log.error("[Paypal] CaptureOrder failed for member: {}, order: {}, code: {}", memberId, orderId, statusCode);
            throw new CustomException(ErrorStatus.PAYPAL_API_ERROR);
        }

        return objectMapper.readValue(content, OrderDto.class);
    }

    private String encodeBasicCredentials() {
        String input = paypalProperties.getClientId() + ":" + paypalProperties.getSecret();
        return "Basic " + Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }
}
