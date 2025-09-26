package com.ecmsp.gatewayservice.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

@Component
public class JwtTokenReader {

    private final PublicKey publicKey;

    public JwtTokenReader(@Value("${jwt.public-key-path}") Resource publicKeyResource) {
        this.publicKey = loadPublicKey(publicKeyResource);
    }

    public JwtClaims readToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new JwtClaims(
                    claims.getSubject(),
                    claims.get("login", String.class),
                    claims.getIssuedAt().getTime() / 1000,
                    claims.getExpiration().getTime() / 1000
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JWT token", e);
        }
    }

    private PublicKey loadPublicKey(Resource publicKeyResource) {
        try {
            String keyContent = StreamUtils.copyToString(publicKeyResource.getInputStream(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(keyContent);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }
}
