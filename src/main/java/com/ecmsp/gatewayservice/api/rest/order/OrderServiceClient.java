package com.ecmsp.gatewayservice.api.rest.order;

import com.ecmsp.gatewayservice.api.rest.order.dto.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
class OrderServiceClient {

    private final RestTemplate restTemplate;
    private final String orderServiceUrl;

    public OrderServiceClient(RestTemplate restTemplate,
                             @Value("${services.order-service.url}") String orderServiceUrl) {
        this.restTemplate = restTemplate;
        this.orderServiceUrl = orderServiceUrl;
    }

    public ResponseEntity<List<Order>> getUserOrders(String userId, String login) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-ID", userId);
        headers.set("X-Login", login);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = orderServiceUrl + "/api/orders/user/" + userId;

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Order>>() {}
        );
    }
}