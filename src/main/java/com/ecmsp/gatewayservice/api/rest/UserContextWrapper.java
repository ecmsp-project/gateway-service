package com.ecmsp.gatewayservice.api.rest;

import com.ecmsp.gatewayservice.api.grpc.user.PermissionsEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Set;

public class UserContextWrapper extends HttpServletRequestWrapper {

    private final String userId;
    private final String login;
    private final Set<String> permissions;

    public UserContextWrapper(HttpServletRequest request, String userId, String login, Set<String> permissions) {
        super(request);
        this.userId = userId;
        this.login = login;
        this.permissions = permissions;
    }

    public String getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(PermissionsEnum permission) {
        return permissions.contains(permission.name());
    }

    public boolean isHimselfOrHasPermission(PermissionsEnum permission) {
        return this.userId.equals(userId) || hasPermission(permission);
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