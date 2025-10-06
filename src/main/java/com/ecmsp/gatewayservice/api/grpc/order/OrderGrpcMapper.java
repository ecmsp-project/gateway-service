package com.ecmsp.gatewayservice.api.grpc.order;

import com.ecmsp.gatewayservice.api.rest.order.dto.Order;
import com.ecmsp.gatewayservice.api.rest.order.dto.OrderItem;
import com.ecmsp.gatewayservice.api.rest.order.dto.OrderStatus;
import com.ecmsp.order.v1.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderGrpcMapper {

    public List<Order> toOrders(ListOrdersByUserIdResponse grpcResponse) {
        return grpcResponse.getOrdersList().stream()
                .map(this::toOrderFromResponse)
                .toList();
    }

    public Order toOrder(GetOrderResponse grpcResponse) {
        List<OrderItem> items = grpcResponse.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();

        return new Order(
                grpcResponse.getOrderId(),
                grpcResponse.getClientId(),
                grpcResponse.getOrderStatus().name(),
                grpcResponse.getDate(),
                items
        );
    }

    public List<OrderItem> toOrderItems(GetOrderItemsResponse grpcResponse) {
        return grpcResponse.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();
    }

    public OrderStatus toOrderStatus(GetOrderStatusResponse grpcResponse) {
        return new OrderStatus(
                grpcResponse.getOrderId(),
                grpcResponse.getOrderStatus().name()
        );
    }

    private Order toOrderFromResponse(GetOrderResponse grpcOrder) {
        List<OrderItem> items = grpcOrder.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();

        return new Order(
                grpcOrder.getOrderId(),
                grpcOrder.getClientId(),
                grpcOrder.getOrderStatus().name(),
                grpcOrder.getDate(),
                items
        );
    }

    private OrderItem toOrderItem(OrderItemDetails grpcItem) {
        return new OrderItem(
                grpcItem.getItemId(),
                grpcItem.getQuantity()
        );
    }
}
