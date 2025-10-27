package com.ecmsp.gatewayservice.application.security;

record JwtClaims(
        String userId,
        String login,
        Long issuedAt,
        Long expiration
) {
    public boolean isExpired() {
        if (expiration == null) return false;
        long nowSec = System.currentTimeMillis() / 1000;
        return expiration < nowSec;
    }
}