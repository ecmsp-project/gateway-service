package com.ecmsp.gatewayservice.api.grpc.cart;

import com.ecmsp.cart.v1.*;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartDto;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartProductDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartGrpcMapper {

    public CartDto toCartDto(GetCartResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(grpcProduct.getProductId(), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }

    public CartDto toCartDto(AddProductResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(grpcProduct.getProductId(), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }

    public CartDto toCartDto(DeleteProductResponse grpcResponse) {
        List<CartProductDto> cartProductsGrpcList =
                grpcResponse.getCart().getCartProductsList().stream()
                        .map(grpcProduct ->
                                new CartProductDto(grpcProduct.getProductId(), grpcProduct.getQuantity())
                        ).toList();

        return new CartDto(cartProductsGrpcList);
    }



}
