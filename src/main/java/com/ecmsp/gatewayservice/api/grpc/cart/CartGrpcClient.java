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

    public UpdateQuantitiesResponse updateQuantitiesRequest(CartDto cartDto, UserContextWrapper wrapper) {
        List<CartProductDto> cartProductsDtos = cartDto.productDtos();
        List<CartProduct> cartProductsGrpc = cartProductsDtos.stream()
                .map(dto ->
                        CartProduct.newBuilder().setProductId(dto.productId()).setQuantity(dto.quantity()).build())
                .toList();
        Cart cart = Cart.newBuilder().addAllCartProducts(cartProductsGrpc).build();

        UpdateQuantitiesRequest request = UpdateQuantitiesRequest.newBuilder().setCart(cart).build();

        return stubWithMetadata(wrapper).updateQuantities(request);


    }


    private CartServiceGrpc.CartServiceBlockingStub stubWithMetadata(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);

        return cartServiceBlockingStub.withCallCredentials(credentials);

    }
}
