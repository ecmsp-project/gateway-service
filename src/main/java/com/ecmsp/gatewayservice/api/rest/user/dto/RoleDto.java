package com.ecmsp.gatewayservice.api.rest.user.dto;

import java.util.List;

public record RoleDto(
        String name,
        List<String> permissions
) {
}
