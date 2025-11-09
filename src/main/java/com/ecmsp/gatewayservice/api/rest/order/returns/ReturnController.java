package com.ecmsp.gatewayservice.api.rest.order.returns;

import com.ecmsp.gatewayservice.api.rest.order.returns.dto.CreateReturnResponseDto;
import com.ecmsp.gatewayservice.api.grpc.order.returns.ReturnGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.order.returns.ReturnGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.order.returns.dto.ReturnOrder;
import com.ecmsp.gatewayservice.api.rest.order.returns.dto.ReturnToCreate;
import com.ecmsp.order.v1.returns.v1.CreateReturnRequest;
import com.ecmsp.order.v1.returns.v1.CreateReturnResponse;
import com.ecmsp.order.v1.returns.v1.GetReturnResponse;
import com.ecmsp.order.v1.returns.v1.ListReturnsByUserIdResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
public class ReturnController {

    private final ReturnGrpcClient returnGrpcClient;
    private final ReturnGrpcMapper returnGrpcMapper;

    public ReturnController(ReturnGrpcClient returnGrpcClient,
                            ReturnGrpcMapper returnGrpcMapper) {
        this.returnGrpcClient = returnGrpcClient;
        this.returnGrpcMapper = returnGrpcMapper;
    }


    @PostMapping
    public ResponseEntity<CreateReturnResponseDto> createReturn(
            @RequestBody @Valid ReturnToCreate returnToCreate,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        CreateReturnRequest grpcRequest = returnGrpcMapper.toCreateReturnRequest(returnToCreate);
        CreateReturnResponse grpcResponse = returnGrpcClient.createReturn(grpcRequest, wrapper);
        CreateReturnResponseDto response = returnGrpcMapper.toCreateResponseDto(grpcResponse);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @GetMapping
    public ResponseEntity<List<ReturnOrder>> getReturns(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        ListReturnsByUserIdResponse grpcResponse = returnGrpcClient.listReturnsByUserId(wrapper);
        List<ReturnOrder> returns = returnGrpcMapper.toReturnOrders(grpcResponse);
        return ResponseEntity.ok(returns);
    }


    @GetMapping("/{returnId}")
    public ResponseEntity<ReturnOrder> getReturn(
            @PathVariable String returnId,
            HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetReturnResponse grpcResponse = returnGrpcClient.getReturn(returnId, wrapper);
        ReturnOrder returnOrder = returnGrpcMapper.toReturnOrder(grpcResponse);
        return ResponseEntity.ok(returnOrder);
    }
}