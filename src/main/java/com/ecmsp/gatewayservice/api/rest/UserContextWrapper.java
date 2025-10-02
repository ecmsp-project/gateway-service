package com.ecmsp.gatewayservice.api.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class UserContextWrapper extends HttpServletRequestWrapper {

    private final String userId;
    private final String login;

    public UserContextWrapper(HttpServletRequest request, String userId, String login) {
        super(request);
        this.userId = userId;
        this.login = login;
    }

    public String getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getHeader(String name) {
        if ("X-User-ID".equals(name)) {
            return userId;
        }
        if ("X-Login".equals(name)) {
            return login;
        }
        return super.getHeader(name);
    }
}