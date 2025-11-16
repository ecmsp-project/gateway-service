package com.ecmsp.gatewayservice.api.rest.auth;


import com.ecmsp.gatewayservice.application.exception.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthClient authClient;

    public AuthController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequest request) {
        AuthResponseDto response = authClient.authenticate(request.login(), request.password());
        return ResponseEntity.ok(response);
    }
}