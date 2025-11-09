package com.ecmsp.gatewayservice.api.rest.user;

import com.ecmsp.gatewayservice.api.grpc.user.UserGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.user.UserGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserDto;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserToCreateDto;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.user.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserGrpcClient userGrpcClient;
    private final UserGrpcMapper userGrpcMapper;

    public UserController(UserGrpcClient userGrpcClient, UserGrpcMapper userGrpcMapper) {
        this.userGrpcClient = userGrpcClient;
        this.userGrpcMapper = userGrpcMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(
            HttpServletRequest request,
            @PathVariable String userId) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        GetUserResponse grpcResponse = userGrpcClient.getUser(userId, wrapper);
        UserDto userDto = userGrpcMapper.toUserDto(grpcResponse);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            HttpServletRequest request,
            @PathVariable String userId,
            @RequestBody UserDto userDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        // Ensure the path userId matches the DTO userId
        if (!userId.equals(userDto.id())) {
            return ResponseEntity.badRequest().build();
        }

        UpdateUserResponse grpcResponse = userGrpcClient.updateUser(userDto, wrapper);
        UserDto updatedUserDto = userGrpcMapper.toUserDto(grpcResponse);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            HttpServletRequest request,
            @PathVariable String userId) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        userGrpcClient.deleteUser(userId, wrapper);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers(
            HttpServletRequest request,
            @RequestParam(required = false) String filterLogin) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        ListUsersResponse grpcResponse = userGrpcClient.listUsers(filterLogin, wrapper);
        List<UserDto> users = userGrpcMapper.toUserListDto(grpcResponse);
        return ResponseEntity.ok(users);
    }
}
