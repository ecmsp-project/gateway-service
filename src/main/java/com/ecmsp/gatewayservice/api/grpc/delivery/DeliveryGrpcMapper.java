package com.ecmsp.gatewayservice.api.grpc.delivery;

import com.ecmsp.gatewayservice.api.rest.delivery.dto.*;
import com.ecmsp.product.v1.delivery.v1.*;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class DeliveryGrpcMapper {

    public RecordDeliveryRequest toGrpcRecordDeliveryRequest(RecordDeliveryRequestDto requestDto) {
        List<DeliveryItem> grpcItems = requestDto.items().stream()
                .map(this::toGrpcDeliveryItem)
                .toList();

        return RecordDeliveryRequest.newBuilder()
                .addAllItems(grpcItems)
                .build();
    }

    public RecordDeliveryResponseDto toRecordDeliveryResponse(RecordDeliveryResponse grpcResponse) {
        String recordedAt = timestampToString(grpcResponse.getRecordedAt());

        return new RecordDeliveryResponseDto(
                grpcResponse.getDeliveryId(),
                recordedAt
        );
    }

    public ListDeliveriesRequest toGrpcListDeliveriesRequest(ListDeliveriesRequestDto requestDto) {
        ListDeliveriesRequest.Builder builder = ListDeliveriesRequest.newBuilder()
                .setVariantId(requestDto.variantId());

        if (requestDto.fromDate() != null && !requestDto.fromDate().isEmpty()) {
            builder.setFromDate(stringToTimestamp(requestDto.fromDate()));
        }

        if (requestDto.toDate() != null && !requestDto.toDate().isEmpty()) {
            builder.setToDate(stringToTimestamp(requestDto.toDate()));
        }

        return builder.build();
    }

    public ListDeliveriesResponseDto toListDeliveriesResponse(ListDeliveriesResponse grpcResponse) {
        List<DeliveryDto> deliveries = grpcResponse.getDeliveriesList().stream()
                .map(this::toDeliveryDto)
                .toList();

        return new ListDeliveriesResponseDto(deliveries);
    }

    private DeliveryDto toDeliveryDto(Delivery grpcDelivery) {
        List<DeliveryItemDto> items = grpcDelivery.getItemsList().stream()
                .map(this::toDeliveryItemDto)
                .toList();

        String recordedAt = timestampToString(grpcDelivery.getRecordedAt());

        return new DeliveryDto(
                grpcDelivery.getDeliveryId(),
                items,
                recordedAt
        );
    }

    private DeliveryItem toGrpcDeliveryItem(DeliveryItemDto itemDto) {
        return DeliveryItem.newBuilder()
                .setVariantId(itemDto.variantId())
                .setQuantity(itemDto.quantity())
                .build();
    }

    private DeliveryItemDto toDeliveryItemDto(DeliveryItem grpcItem) {
        return new DeliveryItemDto(
                grpcItem.getVariantId(),
                grpcItem.getQuantity()
        );
    }

    private String timestampToString(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        Instant instant = Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos());
        return instant.toString();
    }

    private Timestamp stringToTimestamp(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        Instant instant = Instant.parse(dateString);
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
