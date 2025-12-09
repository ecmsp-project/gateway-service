package com.ecmsp.gatewayservice.api.rest.product;

import com.ecmsp.gatewayservice.api.grpc.product.ProductGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.product.ProductGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.product.dto.CreateProductRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.CreateProductResponseDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.DeleteProductRequestDto;
import com.ecmsp.gatewayservice.api.rest.product.dto.DeleteProductResponseDto;
import com.ecmsp.product.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductGrpcClient productGrpcClient;
    private final ProductGrpcMapper productGrpcMapper;

    public ProductController(ProductGrpcClient productGrpcClient, ProductGrpcMapper productGrpcMapper) {
        this.productGrpcClient = productGrpcClient;
        this.productGrpcMapper = productGrpcMapper;
    }

    @PostMapping("/grpc")
    public ResponseEntity<CreateProductResponseDto> createProduct(
            HttpServletRequest request,
            @RequestBody CreateProductRequestDto requestDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            CreateProductRequest grpcRequest = productGrpcMapper.toGrpcCreateProductRequest(requestDto);
            CreateProductResponse grpcResponse = productGrpcClient.createProduct(grpcRequest, wrapper);
            CreateProductResponseDto response = productGrpcMapper.toCreateProductResponse(grpcResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/grpc/{productId}")
    public ResponseEntity<DeleteProductResponseDto> deleteProduct(
            @PathVariable String productId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            DeleteProductRequestDto requestDto = new DeleteProductRequestDto(UUID.fromString(productId));
            DeleteProductRequest grpcRequest = productGrpcMapper.toGrpcDeleteProductRequest(requestDto);
            DeleteProductResponse grpcResponse = productGrpcClient.deleteProduct(grpcRequest, wrapper);
            DeleteProductResponseDto response = productGrpcMapper.toDeleteProductResponse(grpcResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
