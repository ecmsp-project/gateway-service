package com.ecmsp.gatewayservice.application.security;

import java.util.Set;

record JwtClaims(
        String userId,
        String login,
        Set<String> permissions,
        Long issuedAt,
        Long expiration
) {
    public boolean isExpired() {
        if (expiration == null) return false;
        long nowSec = System.currentTimeMillis() / 1000;
        return expiration < nowSec;
    }
}