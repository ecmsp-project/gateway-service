package com.ecmsp.gatewayservice.api.rest.user.dto;

public record UserToCreateDto(
        String login,
        String password
) {
}
