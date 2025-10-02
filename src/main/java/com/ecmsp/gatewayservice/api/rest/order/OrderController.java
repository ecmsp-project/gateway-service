package com.ecmsp.gatewayservice.api.rest.order;

import com.ecmsp.gatewayservice.api.grpc.order.OrderGrpcClient;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.order.dto.Order;
import com.ecmsp.gatewayservice.api.grpc.order.mapper.OrderGrpcMapper;
import com.ecmsp.order.v1.ListOrdersByUserIdResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/me")
    public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        String userId = wrapper.getUserId();
        String login = wrapper.getLogin();

        try {
            ResponseEntity<List<Order>> response = orderServiceClient.getUserOrders(userId, login);
            return ResponseEntity
                    .status(response.getStatusCode())
                    .body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GetMapping("/me/grpc")
    public ResponseEntity<List<Order>> getMyOrdersViaGrpc(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        String userId = wrapper.getUserId();
        String login = wrapper.getLogin();

        try {
            ListOrdersByUserIdResponse grpcResponse = orderGrpcClient.listOrdersByUserId(userId, login);
            List<Order> orders = orderGrpcMapper.toOrders(grpcResponse);

            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }




}