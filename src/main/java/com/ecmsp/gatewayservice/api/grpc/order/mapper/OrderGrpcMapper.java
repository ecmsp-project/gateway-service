package com.ecmsp.gatewayservice.api.grpc.order.mapper;

import com.ecmsp.gatewayservice.api.rest.order.dto.Order;
import com.ecmsp.gatewayservice.api.rest.order.dto.OrderItem;
import com.ecmsp.order.v1.ListOrdersByUserIdResponse;
import com.ecmsp.order.v1.OrderItemDetails;
import com.ecmsp.order.v1.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderGrpcMapper {

    public List<Order> toOrders(ListOrdersByUserIdResponse grpcResponse) {
        return grpcResponse.getOrdersList().stream()
                .map(this::toOrder)
                .toList();
    }

    private Order toOrder(OrderResponse grpcOrder) {
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
