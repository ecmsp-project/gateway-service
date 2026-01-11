package com.ecmsp.gatewayservice.api.rest.payment;

import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.payment.dto.PaymentDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl;

    public PaymentController(
        RestTemplate restTemplate,
        @Value("${services.payment-service.url}") String paymentServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.paymentServiceUrl = paymentServiceUrl;
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentDto> getPaymentByOrderId(HttpServletRequest request, @PathVariable UUID orderId) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-ID", wrapper.getUserId());
        headers.set("X-Login", wrapper.getLogin());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = paymentServiceUrl + "/api/v1/payments/order/" + orderId;

        var response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            PaymentDto.class
        );

        return ResponseEntity
            .status(response.getStatusCode())
            .body(response.getBody());
    }


    @PostMapping("/{paymentLink}")
    public ResponseEntity<PaymentDto> processPayment(HttpServletRequest request, @PathVariable String paymentLink) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-ID", wrapper.getUserId());
        headers.set("X-Login", wrapper.getLogin());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = paymentServiceUrl + "/api/v1/payments/" + paymentLink;

        var response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            PaymentDto.class
        );

        return ResponseEntity
            .status(response.getStatusCode())
            .body(response.getBody());
    }

}
