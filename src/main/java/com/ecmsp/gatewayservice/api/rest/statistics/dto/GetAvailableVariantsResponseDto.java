package com.ecmsp.gatewayservice.api.rest.statistics.dto;

import java.util.List;

public record GetAvailableVariantsResponseDto(
        List<VariantInfoDto> variants
) {
}
