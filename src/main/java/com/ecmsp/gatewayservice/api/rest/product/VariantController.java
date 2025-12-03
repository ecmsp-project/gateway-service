package com.ecmsp.gatewayservice.api.rest.product;

import com.ecmsp.gatewayservice.api.grpc.product.variant.VariantGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.product.variant.VariantGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.CreateVariantRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.CreateVariantResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.DeleteVariantRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.variant.DeleteVariantResponseDto;
import com.ecmsp.product.v1.variant.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/variants")
public class VariantController {

    private final VariantGrpcClient variantGrpcClient;
    private final VariantGrpcMapper variantGrpcMapper;

    public VariantController(VariantGrpcClient variantGrpcClient, VariantGrpcMapper variantGrpcMapper) {
        this.variantGrpcClient = variantGrpcClient;
        this.variantGrpcMapper = variantGrpcMapper;
    }

    @PostMapping("/grpc")
    public ResponseEntity<CreateVariantResponseDto> createVariant(
            @RequestBody CreateVariantRequestDto requestDto,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            CreateVariantRequest grpcRequest = variantGrpcMapper.toGrpcCreateVariantRequest(requestDto);
            CreateVariantResponse grpcResponse = variantGrpcClient.createVariant(grpcRequest, wrapper);
            CreateVariantResponseDto response = variantGrpcMapper.toCreateVariantResponse(grpcResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/grpc/{variantId}")
    public ResponseEntity<DeleteVariantResponseDto> deleteVariant(
            @PathVariable String variantId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            DeleteVariantRequestDto requestDto = new DeleteVariantRequestDto(UUID.fromString(variantId));
            DeleteVariantRequest grpcRequest = variantGrpcMapper.toGrpcDeleteVariantRequest(requestDto);
            DeleteVariantResponse grpcResponse = variantGrpcClient.deleteVariant(grpcRequest, wrapper);
            DeleteVariantResponseDto response = variantGrpcMapper.toDeleteVariantResponse(grpcResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
