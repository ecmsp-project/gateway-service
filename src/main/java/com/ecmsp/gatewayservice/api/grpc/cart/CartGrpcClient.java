package com.ecmsp.gatewayservice.api.grpc.cart;

import com.ecmsp.cart.v1.*;
import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartDto;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartProductDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartGrpcClient {

    @GrpcClient("cart-service")
    private CartServiceGrpc.CartServiceBlockingStub cartServiceBlockingStub;

    public GetCartResponse getUserCart(UserContextWrapper wrapper) {

        GetCartRequest request = GetCartRequest.newBuilder().build();

        return stubWithMetadata(wrapper).getCart(request);
    }

    public AddProductResponse addProductToCart(CartProductDto cartProductDto, UserContextWrapper wrapper) {

        AddProductRequest request = AddProductRequest.newBuilder()
                .setProduct(
                        ProductRequest.newBuilder()
                                .setProductId(cartProductDto.productId())
                                .setQuantity(cartProductDto.quantity())
                                .build()
                ).build();

        return stubWithMetadata(wrapper).addProduct(request);
    }

    public DeleteProductResponse deleteProductFromCart(CartProductDto cartProductDto, UserContextWrapper wrapper) {

        DeleteProductRequest request = DeleteProductRequest.newBuilder()
                .setProductId(cartProductDto.productId())
                .build();

        return stubWithMetadata(wrapper).deleteProduct(request);
    }

    public DeleteCartResponse deleteCartRequest(DeleteProductRequest deleteProductRequest, UserContextWrapper wrapper) {
        DeleteCartRequest request = DeleteCartRequest.newBuilder().build();

        return stubWithMetadata(wrapper).deleteCart(request);
    }

    public UpdateQuantityResponse updateQuantityRequest(CartProductDto cartProductDto, UserContextWrapper wrapper) {
        ProductRequest productRequest = ProductRequest.newBuilder().setProductId(cartProductDto.productId()).setQuantity(cartProductDto.quantity()).build();
        UpdateQuantityRequest request = UpdateQuantityRequest.newBuilder().setProduct(productRequest).build();

        return stubWithMetadata(wrapper).updateQuantity(request);
    }


    private CartServiceGrpc.CartServiceBlockingStub stubWithMetadata(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return cartServiceBlockingStub.withCallCredentials(credentials);

    }

    public SubtractProductResponse subtractProductFromCart(CartProductDto cartProductDto, UserContextWrapper wrapper) {
        ProductRequest productRequest = ProductRequest.newBuilder()
                .setProductId(cartProductDto.productId())
                .setQuantity(cartProductDto.quantity())
                .build();

        SubtractProductRequest request = SubtractProductRequest.newBuilder()
                .setProduct(productRequest)
                .build();

        return stubWithMetadata(wrapper).subtractProduct(request);
    }
}
