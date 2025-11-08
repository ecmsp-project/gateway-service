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

        try {
            GetUserResponse grpcResponse = userGrpcClient.getUser(userId, wrapper);
            UserDto userDto = userGrpcMapper.toUserDto(grpcResponse);
            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            log.error("Exception from getUser: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            HttpServletRequest request,
            @RequestBody UserToCreateDto userToCreateDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            CreateUserResponse grpcResponse = userGrpcClient.createUser(userToCreateDto, wrapper);
            UserDto userDto = userGrpcMapper.toUserDto(grpcResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            HttpServletRequest request,
            @PathVariable String userId,
            @RequestBody UserDto userDto) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            // Ensure the path userId matches the DTO userId
            if (!userId.equals(userDto.id())) {
                return ResponseEntity.badRequest().build();
            }

            UpdateUserResponse grpcResponse = userGrpcClient.updateUser(userDto, wrapper);
            UserDto updatedUserDto = userGrpcMapper.toUserDto(grpcResponse);
            return ResponseEntity.ok(updatedUserDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            HttpServletRequest request,
            @PathVariable String userId) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            userGrpcClient.deleteUser(userId, wrapper);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers(
            HttpServletRequest request,
            @RequestParam(required = false) String filterLogin) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        try {
            ListUsersResponse grpcResponse = userGrpcClient.listUsers(filterLogin, wrapper);
            List<UserDto> users = userGrpcMapper.toUserListDto(grpcResponse);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Exception from listUsers: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
