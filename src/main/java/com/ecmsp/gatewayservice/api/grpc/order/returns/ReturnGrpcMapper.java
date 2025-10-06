package com.ecmsp.gatewayservice.api.grpc.order.returns;

import com.ecmsp.gatewayservice.api.rest.order.returns.dto.ItemToReturnDetails;
import com.ecmsp.gatewayservice.api.rest.order.returns.dto.ReturnOrder;
import com.ecmsp.gatewayservice.api.rest.order.returns.dto.ReturnToCreate;
import com.ecmsp.order.v1.returns.v1.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReturnGrpcMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public CreateReturnRequest toCreateReturnRequest(ReturnToCreate returnToCreate) {
        List<ItemReturnDetails> items = returnToCreate.itemsToReturn().stream()
                .map(this::toItemReturnDetails)
                .toList();

        return CreateReturnRequest.newBuilder()
                .setOrderId(returnToCreate.orderId())
                .addAllItems(items)
                .build();
    }

    public ReturnOrder toReturnOrder(CreateReturnResponse grpcResponse) {
        return new ReturnOrder(
                grpcResponse.getReturnId(),
                null, // orderId not included in CreateReturnResponse
                List.of(),
                grpcResponse.getStatus(),
                null  // createdAt not included in CreateReturnResponse
        );
    }

    public ReturnOrder toReturnOrder(GetReturnResponse grpcResponse) {
        Return returnProto = grpcResponse.getReturn();

        List<ItemToReturnDetails> items = returnProto.getItemsList().stream()
                .map(this::toItemToReturnDetails)
                .toList();

        LocalDateTime createdAt = returnProto.getCreatedAt().isEmpty()
                ? null
                : LocalDateTime.parse(returnProto.getCreatedAt(), FORMATTER);

        return new ReturnOrder(
                returnProto.getReturnId(),
                returnProto.getOrderId(),
                items,
                returnProto.getStatus(),
                createdAt
        );
    }

    public List<ReturnOrder> toReturnOrders(ListReturnsByUserIdResponse grpcResponse) {
        return grpcResponse.getReturnsList().stream()
                .map(this::toReturnOrderFromProto)
                .toList();
    }

    public List<ReturnOrder> toReturnOrders(ListReturnsResponse grpcResponse) {
        return grpcResponse.getReturnsList().stream()
                .map(this::toReturnOrderFromProto)
                .toList();
    }

    private ReturnOrder toReturnOrderFromProto(Return returnProto) {
        List<ItemToReturnDetails> items = returnProto.getItemsList().stream()
                .map(this::toItemToReturnDetails)
                .toList();

        LocalDateTime createdAt = returnProto.getCreatedAt().isEmpty()
                ? null
                : LocalDateTime.parse(returnProto.getCreatedAt(), FORMATTER);

        return new ReturnOrder(
                returnProto.getReturnId(),
                returnProto.getOrderId(),
                items,
                returnProto.getStatus(),
                createdAt
        );
    }

    private ItemReturnDetails toItemReturnDetails(ItemToReturnDetails dto) {
        return ItemReturnDetails.newBuilder()
                .setItemId(dto.itemId())
                .setVariantId(dto.variantId())
                .setQuantity(dto.quantity())
                .setReason(dto.reason())
                .build();
    }

    private ItemToReturnDetails toItemToReturnDetails(ItemReturnDetails proto) {
        return new ItemToReturnDetails(
                proto.getItemId(),
                proto.getVariantId(),
                proto.getQuantity(),
                proto.getReason()
        );
    }
}
