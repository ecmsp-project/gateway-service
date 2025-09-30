package com.ecmsp.gatewayservice.api.rest.order;

import com.ecmsp.gatewayservice.application.security.UserContextWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceClient orderServiceClient;

    public OrderController(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getMyOrders(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        String userId = wrapper.getUserId();
        String login = wrapper.getLogin();

        try {
            ResponseEntity<String> response = orderServiceClient.getUserOrders(userId, login);
            return ResponseEntity
                    .status(response.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Failed to fetch orders\"}");
        }
    }
}