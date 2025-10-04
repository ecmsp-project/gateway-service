package com.ecmsp.gatewayservice.api.rest.order;

import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.order.v1.CreateReturnRequest;
import com.ecmsp.order.v1.CreateReturnResponse;
import com.ecmsp.order.v1.GetReturnResponse;
import com.ecmsp.order.v1.ListReturnsByUserIdResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("/api/returns")
//public class ReturnController {
//
//    private final ReturnGrpcClient returnGrpcClient;
//    private final ReturnGrpcMapper returnGrpcMapper;
//
//    public ReturnController(ReturnGrpcClient returnGrpcClient,
//                            ReturnGrpcMapper returnGrpcMapper) {
//        this.returnGrpcClient = returnGrpcClient;
//        this.returnGrpcMapper = returnGrpcMapper;
//    }
//
//    // Create a return with selected articles/items
//    @PostMapping
//    public ResponseEntity<ReturnResponse> createReturn(
//            @RequestBody @Valid CreateReturnRequest returnRequest,
//            HttpServletRequest request) {
//        UserContextWrapper wrapper = (UserContextWrapper) request;
//        String userId = wrapper.getUserId();
//        String login = wrapper.getLogin();
//
//        try {
//            // returnRequest contains: orderId, selected items, quantities, return reasons
//            CreateReturnResponse grpcResponse = returnGrpcClient.createReturn(
//                    userId, login, returnRequest
//            );
//            ReturnResponse response = returnGrpcMapper.toReturnResponse(grpcResponse);
//            return ResponseEntity
//                    .status(HttpStatus.CREATED)
//                    .body(response);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .build();
//        }
//    }
//
//    // List all returns for the authenticated user
//    @GetMapping
//    public ResponseEntity<List<ReturnSummary>> getMyReturns(HttpServletRequest request) {
//        UserContextWrapper wrapper = (UserContextWrapper) request;
//        String userId = wrapper.getUserId();
//        String login = wrapper.getLogin();
//
//        try {
//            ListReturnsByUserIdResponse grpcResponse = returnGrpcClient.listReturnsByUserId(userId, login);
//            List<ReturnSummary> returns = returnGrpcMapper.toReturnSummaries(grpcResponse);
//            return ResponseEntity.ok(returns);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .build();
//        }
//    }
//
//    // Get specific return details
//    @GetMapping("/{returnId}")
//    public ResponseEntity<ReturnDetails> getReturn(
//            @PathVariable String returnId,
//            HttpServletRequest request) {
//        UserContextWrapper wrapper = (UserContextWrapper) request;
//        String userId = wrapper.getUserId();
//        String login = wrapper.getLogin();
//
//        try {
//            GetReturnResponse grpcResponse = returnGrpcClient.getReturn(returnId, userId, login);
//            ReturnDetails returnDetails = returnGrpcMapper.toReturnDetails(grpcResponse);
//            return ResponseEntity.ok(returnDetails);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .build();
//        }
//    }
//}