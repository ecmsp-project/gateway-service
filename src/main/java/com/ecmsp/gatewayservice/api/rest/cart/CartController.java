package com.ecmsp.gatewayservice.api.rest.cart;

import com.ecmsp.cart.v1.*;
import com.ecmsp.gatewayservice.api.grpc.cart.CartGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.cart.CartGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartDto;
import com.ecmsp.gatewayservice.api.rest.cart.dto.CartProductDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartGrpcClient cartGrpcClient;
    private final CartGrpcMapper cartGrpcMapper;

    public CartController(CartGrpcClient cartGrpcClient, CartGrpcMapper cartGrpcMapper) {
        this.cartGrpcClient = cartGrpcClient;
        this.cartGrpcMapper = cartGrpcMapper;
    }

    @GetMapping()
    public ResponseEntity<CartDto> getUserCart(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetCartResponse grpcResponse = cartGrpcClient.getUserCart(wrapper);
        CartDto cartDto = cartGrpcMapper.toCartDto(grpcResponse);

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<CartDto> addProductToCart(HttpServletRequest request, @RequestBody CartProductDto cartProductDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        AddProductResponse grpcResponse = cartGrpcClient.addProductToCart(cartProductDto, wrapper);
        CartDto cartDto = cartGrpcMapper.toCartDto(grpcResponse);

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<CartDto> deleteProductFromCart(HttpServletRequest request, @RequestBody CartProductDto cartProductDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        DeleteProductResponse grpcResponse = cartGrpcClient.deleteProductFromCart(cartProductDto, wrapper);
        CartDto cartDto = cartGrpcMapper.toCartDto(grpcResponse);

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<CartDto> updateProductQuantity(HttpServletRequest request, @RequestBody CartProductDto cartProductDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        UpdateQuantityResponse grpcResponse = cartGrpcClient.updateQuantityRequest(cartProductDto, wrapper);
        CartDto returnedCartDto = cartGrpcMapper.toCartDto(grpcResponse);

        return ResponseEntity.ok(returnedCartDto);
    }

    @PostMapping("/subtractProduct")
    public ResponseEntity<CartDto> subtractProductFromCart(HttpServletRequest request, @RequestBody CartProductDto cartProductDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        SubtractProductResponse grpcResponse = cartGrpcClient.subtractProductFromCart(cartProductDto, wrapper);
        CartDto returnedCartDto = cartGrpcMapper.toCartDto(grpcResponse);

        return ResponseEntity.ok(returnedCartDto);
    }

}
