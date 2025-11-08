package com.ecmsp.gatewayservice.api.grpc.order;

import com.ecmsp.gatewayservice.api.rest.order.dto.*;
import com.ecmsp.order.v1.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderGrpcMapper {

    public List<GetOrderResponseDto> toOrders(ListOrdersByUserIdResponse grpcResponse) {
        return grpcResponse.getOrdersList().stream()
                .map(this::toOrderFromResponse)
                .toList();
    }

    public GetOrderResponseDto toOrder(GetOrderResponse grpcResponse) {
        List<OrderItemDetailsDto> items = grpcResponse.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();

        return new GetOrderResponseDto(
                grpcResponse.getOrderId(),
                grpcResponse.getOrderStatus().name(),
                grpcResponse.getDate(),
                items
        );
    }

    public List<OrderItemDetailsDto> toOrderItems(GetOrderItemsResponse grpcResponse) {
        return grpcResponse.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();
    }

    public GetOrderStatusResponseDto toOrderStatus(GetOrderStatusResponse grpcResponse) {
        return new GetOrderStatusResponseDto(
                grpcResponse.getOrderId(),
                grpcResponse.getOrderStatus().name()
        );
    }

    private GetOrderResponseDto toOrderFromResponse(GetOrderResponse grpcOrder) {
        List<OrderItemDetailsDto> items = grpcOrder.getItemsList().stream()
                .map(this::toOrderItem)
                .toList();

        return new GetOrderResponseDto(
                grpcOrder.getOrderId(),
                grpcOrder.getOrderStatus().name(),
                grpcOrder.getDate(),
                items
        );
    }

    private OrderItemDetailsDto toOrderItem(OrderItemDetails grpcItem) {
        return new OrderItemDetailsDto(
                grpcItem.getItemId(),
                grpcItem.getVariantId(),
                grpcItem.getQuantity(),
                grpcItem.getPrice(),
                grpcItem.getImageUrl(),
                grpcItem.getDescription(),
                grpcItem.getIsReturnable()
        );
    }

    // Create Order Mapping
    public CreateOrderRequest toGrpcCreateOrderRequest(CreateOrderRequestDto requestDto) {
        List<OrderItemDetails> grpcItems = requestDto.items().stream()
                .map(this::toGrpcOrderItem)
                .toList();

        return CreateOrderRequest.newBuilder()
                .addAllItems(grpcItems)
                .build();
    }

    public CreateOrderResponseDto toCreateOrderResponse(CreateOrderResponse grpcResponse) {
        List<FailedReservationVariantDto> failedVariants = grpcResponse.getFailedVariantsList().stream()
                .map(this::toFailedReservationVariant)
                .toList();

        return new CreateOrderResponseDto(
                grpcResponse.getIsSuccess(),
                grpcResponse.getOrderId(),
                grpcResponse.getReservedVariantIdsList(),
                failedVariants
        );
    }

    private OrderItemDetails toGrpcOrderItem(CreateOrderItemDto item) {
        return OrderItemDetails.newBuilder()
                .setItemId(item.itemId())
                .setVariantId(item.variantId())
                .setName(item.name())
                .setQuantity(item.quantity())
                .setPrice(item.price())
                .setImageUrl(item.imageUrl())
                .setDescription(item.description())
                .setIsReturnable(item.isReturnable())
                .build();
    }

    private FailedReservationVariantDto toFailedReservationVariant(FailedReservationVariant grpcVariant) {
        return new FailedReservationVariantDto(
                grpcVariant.getVariantId(),
                grpcVariant.getRequestedQuantity(),
                grpcVariant.getAvailableQuantity()
        );
    }
}
