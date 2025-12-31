package com.ecmsp.gatewayservice.api.grpc.cart;

import com.ecmsp.cart.v1.*;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartDto;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartProductDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CartGrpcMapper {

    private UUID convertToUUID(String id) {
        return UUID.fromString(id);
    }

    public CartDto toCartDto(GetCartResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(convertToUUID(grpcProduct.getProductId()), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }

    public CartDto toCartDto(AddProductResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(convertToUUID(grpcProduct.getProductId()), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }

    public CartDto toCartDto(DeleteProductResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(convertToUUID(grpcProduct.getProductId()), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }


    public CartDto toCartDto(UpdateQuantityResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(convertToUUID(grpcProduct.getProductId()), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }

    public CartDto toCartDto(SubtractProductResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCat().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(convertToUUID(grpcProduct.getProductId()), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }
}
