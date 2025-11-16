package com.ecmsp.gatewayservice.api.rest.user.dto;

import java.util.List;

public record UserDto(
        String id,
        String login,
        List<RoleDto> roles
) {
}
