package com.ecmsp.gatewayservice.api.rest.role.dto;

import java.util.List;

public record RoleToCreateDto(
        String name,
        List<String> permissions
) {
}
