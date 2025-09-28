package com.ecmsp.gatewayservice.application.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenReader jwtTokenReader;

    public JwtAuthenticationFilter(JwtTokenReader jwtTokenReader) {
        this.jwtTokenReader = jwtTokenReader;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = authHeader.substring(7);
            JwtClaims claims = jwtTokenReader.readToken(token);

            if (claims.isExpired()) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            UserContextWrapper wrapper = new UserContextWrapper(httpRequest, claims.userId(), claims.login());
            chain.doFilter(wrapper, response);

        } catch (Exception e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}