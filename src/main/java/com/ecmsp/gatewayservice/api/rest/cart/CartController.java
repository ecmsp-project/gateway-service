package com.ecmsp.gatewayservice.api.rest.cart;

import com.ecmsp.cart.v1.AddProductResponse;
import com.ecmsp.cart.v1.DeleteProductResponse;
import com.ecmsp.cart.v1.GetCartResponse;
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

        try{
            GetCartResponse grpcResponse = cartGrpcClient.getUserCart(wrapper);
            CartDto cartDto = cartGrpcMapper.toCartDto(grpcResponse);

            return ResponseEntity.ok(cartDto);
        }
        catch (Exception e) {
            System.out.println("Exception from getUserCart: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<CartDto> addProductToCart(HttpServletRequest request, @RequestBody CartProductDto cartProductDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try{
            AddProductResponse grpcResponse = cartGrpcClient.addProductToCart(cartProductDto, wrapper);
            CartDto cartDto = cartGrpcMapper.toCartDto(grpcResponse);

            return ResponseEntity.ok(cartDto);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PostMapping("/deleteProduct")
    public ResponseEntity<CartDto> deleteProductFromCart(HttpServletRequest request, @RequestBody CartProductDto cartProductDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try{
            DeleteProductResponse grpcResponse = cartGrpcClient.deleteProductFromCart(cartProductDto, wrapper);
            CartDto cartDto = cartGrpcMapper.toCartDto(grpcResponse);

            return ResponseEntity.ok(cartDto);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }



}
