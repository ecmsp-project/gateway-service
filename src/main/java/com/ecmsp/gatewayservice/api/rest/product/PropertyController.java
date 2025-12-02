package com.ecmsp.gatewayservice.api.rest.product;

import com.ecmsp.gatewayservice.api.grpc.product.property.PropertyGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.product.property.PropertyGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.CreatePropertyRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.CreatePropertyResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.DeletePropertyRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.property.DeletePropertyResponseDto;
import com.ecmsp.product.v1.property.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyGrpcClient propertyGrpcClient;
    private final PropertyGrpcMapper propertyGrpcMapper;

    public PropertyController(PropertyGrpcClient propertyGrpcClient, PropertyGrpcMapper propertyGrpcMapper) {
        this.propertyGrpcClient = propertyGrpcClient;
        this.propertyGrpcMapper = propertyGrpcMapper;
    }

    @PostMapping("/grpc")
    public ResponseEntity<CreatePropertyResponseDto> createProperty(
            @RequestBody CreatePropertyRequestDto requestDto,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            CreatePropertyRequest grpcRequest = propertyGrpcMapper.toGrpcCreatePropertyRequest(requestDto);
            CreatePropertyResponse grpcResponse = propertyGrpcClient.createProperty(grpcRequest, wrapper);
            CreatePropertyResponseDto response = propertyGrpcMapper.toCreatePropertyResponse(grpcResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/grpc/{propertyId}")
    public ResponseEntity<DeletePropertyResponseDto> deleteProperty(
            @PathVariable String propertyId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            DeletePropertyRequestDto requestDto = new DeletePropertyRequestDto(UUID.fromString(propertyId));
            DeletePropertyRequest grpcRequest = propertyGrpcMapper.toGrpcDeletePropertyRequest(requestDto);
            DeletePropertyResponse grpcResponse = propertyGrpcClient.deleteProperty(grpcRequest, wrapper);
            DeletePropertyResponseDto response = propertyGrpcMapper.toDeletePropertyResponse(grpcResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
