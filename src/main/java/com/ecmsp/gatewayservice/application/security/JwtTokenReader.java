package com.ecmsp.gatewayservice.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

            // Extract permissions from JWT claim
            List<?> permissionsList = claims.get("permissions", List.class);
            Set<String> permissions = permissionsList != null
                    ? permissionsList.stream()
                            .map(Object::toString)
                            .collect(Collectors.toSet())
                    : Set.of();

            return new JwtClaims(
                    claims.getSubject(),
                    claims.get("login", String.class),
                    permissions,
                    claims.getIssuedAt() != null ? claims.getIssuedAt().getTime() / 1000 : null,
                    claims.getExpiration() != null ? claims.getExpiration().getTime() / 1000 : null
            );

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT expired", e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Malformed JWT", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature", e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Unsupported JWT", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("JWT argument error", e);
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
