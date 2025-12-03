package com.ecmsp.gatewayservice.api.rest.product;

import com.ecmsp.gatewayservice.api.grpc.product.category.CategoryGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.product.category.CategoryGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.CreateCategoryRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.CreateCategoryResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.DeleteCategoryRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.category.DeleteCategoryResponseDto;
import com.ecmsp.product.v1.category.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryGrpcClient categoryGrpcClient;
    private final CategoryGrpcMapper categoryGrpcMapper;

    public CategoryController(CategoryGrpcClient categoryGrpcClient, CategoryGrpcMapper categoryGrpcMapper) {
        this.categoryGrpcClient = categoryGrpcClient;
        this.categoryGrpcMapper = categoryGrpcMapper;
    }

    @PostMapping("/grpc")
    public ResponseEntity<CreateCategoryResponseDto> createCategory(
            @RequestBody CreateCategoryRequestDto requestDto,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            CreateCategoryRequest grpcRequest = categoryGrpcMapper.toGrpcCreateCategoryRequest(requestDto);
            CreateCategoryResponse grpcResponse = categoryGrpcClient.createCategory(grpcRequest, wrapper);
            CreateCategoryResponseDto response = categoryGrpcMapper.toCreateCategoryResponse(grpcResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/grpc/{categoryId}")
    public ResponseEntity<DeleteCategoryResponseDto> deleteCategory(
            @PathVariable String categoryId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            DeleteCategoryRequestDto requestDto = new DeleteCategoryRequestDto(UUID.fromString(categoryId));
            DeleteCategoryRequest grpcRequest = categoryGrpcMapper.toGrpcDeleteCategoryRequest(requestDto);
            DeleteCategoryResponse grpcResponse = categoryGrpcClient.deleteCategory(grpcRequest, wrapper);
            DeleteCategoryResponseDto response = categoryGrpcMapper.toDeleteCategoryResponse(grpcResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
