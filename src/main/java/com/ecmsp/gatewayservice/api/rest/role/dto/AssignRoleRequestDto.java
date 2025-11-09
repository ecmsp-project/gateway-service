package com.ecmsp.gatewayservice.api.rest.role.dto;

import java.util.List;

public record AssignRoleRequestDto(
        List<String> userIds
) {
}
