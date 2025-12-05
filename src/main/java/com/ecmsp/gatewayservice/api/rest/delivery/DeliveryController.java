package com.ecmsp.gatewayservice.api.rest.delivery;

import com.ecmsp.gatewayservice.api.grpc.delivery.DeliveryGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.delivery.DeliveryGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.delivery.dto.ListDeliveriesRequestDto;
import com.ecmsp.gatewayservice.api.rest.delivery.dto.ListDeliveriesResponseDto;
import com.ecmsp.gatewayservice.api.rest.delivery.dto.RecordDeliveryRequestDto;
import com.ecmsp.gatewayservice.api.rest.delivery.dto.RecordDeliveryResponseDto;
import com.ecmsp.product.v1.delivery.v1.ListDeliveriesRequest;
import com.ecmsp.product.v1.delivery.v1.ListDeliveriesResponse;
import com.ecmsp.product.v1.delivery.v1.RecordDeliveryRequest;
import com.ecmsp.product.v1.delivery.v1.RecordDeliveryResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private final DeliveryGrpcClient deliveryGrpcClient;
    private final DeliveryGrpcMapper deliveryGrpcMapper;

    public DeliveryController(DeliveryGrpcClient deliveryGrpcClient,
                              DeliveryGrpcMapper deliveryGrpcMapper) {
        this.deliveryGrpcClient = deliveryGrpcClient;
        this.deliveryGrpcMapper = deliveryGrpcMapper;
    }

    @PostMapping
    public ResponseEntity<RecordDeliveryResponseDto> recordDelivery(
            @RequestBody @Valid RecordDeliveryRequestDto recordDeliveryRequest,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        RecordDeliveryRequest grpcRequest = deliveryGrpcMapper.toGrpcRecordDeliveryRequest(recordDeliveryRequest);
        RecordDeliveryResponse grpcResponse = deliveryGrpcClient.recordDelivery(grpcRequest, wrapper);
        RecordDeliveryResponseDto response = deliveryGrpcMapper.toRecordDeliveryResponse(grpcResponse);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ListDeliveriesResponseDto> listDeliveries(
            @RequestParam String variantId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        ListDeliveriesRequestDto requestDto = new ListDeliveriesRequestDto(variantId, fromDate, toDate);
        ListDeliveriesRequest grpcRequest = deliveryGrpcMapper.toGrpcListDeliveriesRequest(requestDto);
        ListDeliveriesResponse grpcResponse = deliveryGrpcClient.listDeliveries(grpcRequest, wrapper);
        ListDeliveriesResponseDto response = deliveryGrpcMapper.toListDeliveriesResponse(grpcResponse);

        return ResponseEntity.ok(response);
    }
}
