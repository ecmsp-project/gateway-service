package com.ecmsp.gatewayservice.api.rest.order;

import com.ecmsp.gatewayservice.api.grpc.order.OrderGrpcClient;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderResponseDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.OrderItemDetailsDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderStatusResponseDto;
import com.ecmsp.gatewayservice.api.grpc.order.OrderGrpcMapper;
import com.ecmsp.order.v1.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceClient orderServiceClient;
    private final OrderGrpcClient orderGrpcClient;
    private final OrderGrpcMapper orderGrpcMapper;

    public OrderController(OrderServiceClient orderServiceClient,
                          OrderGrpcClient orderGrpcClient,
                          OrderGrpcMapper orderGrpcMapper) {
        this.orderServiceClient = orderServiceClient;
        this.orderGrpcClient = orderGrpcClient;
        this.orderGrpcMapper = orderGrpcMapper;
    }


    //TODO: to remove we won't use rest - only for testing
    @GetMapping()
    public ResponseEntity<List<GetOrderResponseDto>> getOrdersViaRest(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        String userId = wrapper.getUserId();
        String login = wrapper.getLogin();

        try {
            ResponseEntity<List<GetOrderResponseDto>> response = orderServiceClient.getUserOrders(userId, login);
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getBody());
        } catch (Exception e) {
            System.out.println("Error fetching orders via REST: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/grpc")
    public ResponseEntity<List<GetOrderResponseDto>> getOrders(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            ListOrdersByUserIdResponse grpcResponse = orderGrpcClient.listOrdersByUserId(wrapper);
            List<GetOrderResponseDto> orders = orderGrpcMapper.toOrders(grpcResponse);

            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/grpc/all")
    public ResponseEntity<List<GetOrderResponseDto>> getAllOrders(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

//        if (!wrapper.hasPermission("ADMIN")) {
//            return ResponseEntity
//                    .status(HttpStatus.FORBIDDEN)
//                    .build();
//        }

        try {
            ListOrdersResponse grpcResponse = orderGrpcClient.listOrders(wrapper);
            List<GetOrderResponseDto> orders = orderGrpcMapper.toOrders(grpcResponse);

            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            System.out.println("Error fetching all orders: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }




    // Get specific order details
    @GetMapping("/grpc/{orderId}")
    public ResponseEntity<GetOrderResponseDto> getOrder(
            @PathVariable String orderId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            GetOrderResponse grpcResponse = orderGrpcClient.getOrder(orderId, wrapper);
            GetOrderResponseDto order = orderGrpcMapper.toOrder(grpcResponse);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // Get order items (with isReturnable flag for frontend)
    @GetMapping("/grpc/{orderId}/items")
    public ResponseEntity<List<OrderItemDetailsDto>> getOrderItems(
            @PathVariable String orderId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            GetOrderItemsResponse grpcResponse = orderGrpcClient.getOrderItems(orderId, wrapper);
            List<OrderItemDetailsDto> items = orderGrpcMapper.toOrderItems(grpcResponse);
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    // Get order tracking/status
    @GetMapping("/grpc/{orderId}/status")
    public ResponseEntity<GetOrderStatusResponseDto> getOrderStatus(
            @PathVariable String orderId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            GetOrderStatusResponse grpcResponse = orderGrpcClient.getOrderStatus(orderId, wrapper);
            GetOrderStatusResponseDto status = orderGrpcMapper.toOrderStatus(grpcResponse);
            return ResponseEntity.ok(status);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }







}