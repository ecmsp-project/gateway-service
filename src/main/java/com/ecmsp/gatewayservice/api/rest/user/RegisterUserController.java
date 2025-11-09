package com.ecmsp.gatewayservice.api.rest.user;

import com.ecmsp.gatewayservice.api.grpc.user.UserGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.user.UserGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserDto;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserToCreateDto;
import com.ecmsp.user.v1.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterUserController {

    private final UserGrpcClient userGrpcClient;
    private final UserGrpcMapper userGrpcMapper;

    public RegisterUserController(UserGrpcClient userGrpcClient, UserGrpcMapper userGrpcMapper) {
        this.userGrpcClient = userGrpcClient;
        this.userGrpcMapper = userGrpcMapper;
    }

    @PostMapping("/api/register")
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserToCreateDto userToCreateDto) {
        try {
            CreateUserResponse grpcResponse = userGrpcClient.createUser(userToCreateDto);
            UserDto userDto = userGrpcMapper.toUserDto(grpcResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (Exception e) {
            log.error("Exception from createUser: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
