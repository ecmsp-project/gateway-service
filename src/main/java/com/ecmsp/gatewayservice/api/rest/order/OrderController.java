package com.ecmsp.gatewayservice.api.rest.order;

import com.ecmsp.gatewayservice.api.grpc.order.OrderGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.order.OrderGrpcMapper;
import com.ecmsp.gatewayservice.api.grpc.user.PermissionsEnum;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderResponseDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderStatusResponseDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.OrderItemDetailsDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.GetOrderStatusResponseDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.CreateOrderRequestDto;
import com.ecmsp.gatewayservice.api.rest.order.dto.CreateOrderResponseDto;
import com.ecmsp.gatewayservice.api.grpc.order.OrderGrpcMapper;
import com.ecmsp.order.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecmsp.gatewayservice.api.grpc.user.PermissionsEnum.READ_ORDERS;

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

        ResponseEntity<List<GetOrderResponseDto>> response = orderServiceClient.getUserOrders(userId, login);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response.getBody());
    }

    @GetMapping("/grpc")
    public ResponseEntity<List<GetOrderResponseDto>> getOrders(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        ListOrdersByUserIdResponse grpcResponse = orderGrpcClient.listOrdersByUserId(wrapper);
        List<GetOrderResponseDto> orders = orderGrpcMapper.toOrders(grpcResponse);

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/grpc/all")
    public ResponseEntity<List<GetOrderResponseDto>> getAllOrders(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        ListOrdersResponse grpcResponse = orderGrpcClient.listOrders(wrapper);
        List<GetOrderResponseDto> orders = orderGrpcMapper.toOrders(grpcResponse);

        return ResponseEntity.ok(orders);
    }




    // Get specific order details
    @GetMapping("/grpc/{orderId}")
    public ResponseEntity<GetOrderResponseDto> getOrder(
            @PathVariable String orderId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetOrderResponse grpcResponse = orderGrpcClient.getOrder(orderId, wrapper);
        GetOrderResponseDto order = orderGrpcMapper.toOrder(grpcResponse);
        return ResponseEntity.ok(order);
    }

    // Get order items (with isReturnable flag for frontend)
    @GetMapping("/grpc/{orderId}/items")
    public ResponseEntity<List<OrderItemDetailsDto>> getOrderItems(
            @PathVariable String orderId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetOrderItemsResponse grpcResponse = orderGrpcClient.getOrderItems(orderId, wrapper);
        List<OrderItemDetailsDto> items = orderGrpcMapper.toOrderItems(grpcResponse);
        return ResponseEntity.ok(items);
    }

    // Get order tracking/status
    @GetMapping("/grpc/{orderId}/status")
    public ResponseEntity<GetOrderStatusResponseDto> getOrderStatus(
            @PathVariable String orderId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetOrderStatusResponse grpcResponse = orderGrpcClient.getOrderStatus(orderId, wrapper);
        GetOrderStatusResponseDto status = orderGrpcMapper.toOrderStatus(grpcResponse);
        return ResponseEntity.ok(status);

    }

    // Create new order
    @PostMapping("/grpc")
    public ResponseEntity<CreateOrderResponseDto> createOrder(
            @RequestBody CreateOrderRequestDto requestDto,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            CreateOrderRequest grpcRequest = orderGrpcMapper.toGrpcCreateOrderRequest(requestDto);
            CreateOrderResponse grpcResponse = orderGrpcClient.createOrder(grpcRequest, wrapper);
            CreateOrderResponseDto response = orderGrpcMapper.toCreateOrderResponse(grpcResponse);

            return ResponseEntity
                    .status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }









}