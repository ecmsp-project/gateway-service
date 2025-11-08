package com.ecmsp.gatewayservice.api.grpc.user;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserDto;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserToCreateDto;
import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.user.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class UserGrpcClient {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    public GetUserResponse getUser(String userId, UserContextWrapper wrapper) {
        GetUserRequest request = GetUserRequest.newBuilder()
                .setUserId(userId)
                .build();

        return stubWithMetadata(wrapper).getUser(request);
    }

    public CreateUserResponse createUser(UserToCreateDto userToCreateDto, UserContextWrapper wrapper) {
        UserToCreate userToCreate = UserToCreate.newBuilder()
                .setLogin(userToCreateDto.login())
                .setPassword(userToCreateDto.password())
                .build();

        CreateUserRequest request = CreateUserRequest.newBuilder()
                .setUserToCreate(userToCreate)
                .build();

        return stubWithMetadata(wrapper).createUser(request);
    }

    public UpdateUserResponse updateUser(UserDto userDto, UserContextWrapper wrapper) {
        UserId userId = UserId.newBuilder()
                .setValue(userDto.id())
                .build();

        User.Builder userBuilder = User.newBuilder()
                .setId(userId)
                .setLogin(userDto.login());

        // Add roles if present
        if (userDto.roles() != null) {
            userDto.roles().forEach(roleDto -> {
                RoleId roleId = RoleId.newBuilder()
                        .setValue(roleDto.id())
                        .build();

                Role role = Role.newBuilder()
                        .setId(roleId)
                        .setName(roleDto.name())
                        .addAllPermissions(roleDto.permissions())
                        .build();

                userBuilder.addRoles(role);
            });
        }

        UpdateUserRequest request = UpdateUserRequest.newBuilder()
                .setUser(userBuilder.build())
                .build();

        return stubWithMetadata(wrapper).updateUser(request);
    }

    public DeleteUserResponse deleteUser(String userId, UserContextWrapper wrapper) {
        DeleteUserRequest request = DeleteUserRequest.newBuilder()
                .setUserId(userId)
                .build();

        return stubWithMetadata(wrapper).deleteUser(request);
    }

    public ListUsersResponse listUsers(String filterLogin, UserContextWrapper wrapper) {
        ListUsersRequest.Builder requestBuilder = ListUsersRequest.newBuilder();

        if (filterLogin != null && !filterLogin.isEmpty()) {
            requestBuilder.setFilterLogin(filterLogin);
        }

        return stubWithMetadata(wrapper).listUsers(requestBuilder.build());
    }

    private UserServiceGrpc.UserServiceBlockingStub stubWithMetadata(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);
        return userServiceBlockingStub.withCallCredentials(credentials);
    }
}
