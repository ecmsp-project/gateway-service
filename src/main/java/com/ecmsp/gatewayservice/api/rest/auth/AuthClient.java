package com.ecmsp.gatewayservice.api.rest.auth;

import com.ecmsp.gatewayservice.application.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
class AuthClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    public AuthClient(RestTemplate restTemplate,
                      @Value("${services.user-service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public AuthResponseDto authenticate(String login, String password) {
        AuthRequest request = new AuthRequest(login, password);

        ResponseEntity<AuthResponseDto> response = restTemplate.postForEntity(
                userServiceUrl + "/api/auth/authenticate",
                request,
                AuthResponseDto.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        throw new AuthenticationException("Authentication failed");
    }
}