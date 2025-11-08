package com.ecmsp.gatewayservice.api.grpc.user;

import com.ecmsp.gatewayservice.api.rest.user.dto.RoleDto;
import com.ecmsp.gatewayservice.api.rest.user.dto.UserDto;
import com.ecmsp.user.v1.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGrpcMapper {

    public UserDto toUserDto(GetUserResponse grpcResponse) {
        return mapUser(grpcResponse.getUser());
    }

    public UserDto toUserDto(CreateUserResponse grpcResponse) {
        return mapUser(grpcResponse.getUser());
    }

    public UserDto toUserDto(UpdateUserResponse grpcResponse) {
        return mapUser(grpcResponse.getUser());
    }

    public List<UserDto> toUserListDto(ListUsersResponse grpcResponse) {
        return grpcResponse.getUsersList().stream()
                .map(this::mapUser)
                .toList();
    }

    private UserDto mapUser(User grpcUser) {
        List<RoleDto> roles = grpcUser.getRolesList().stream()
                .map(this::toRoleDto)
                .toList();

        return new UserDto(
                grpcUser.getId().getValue(),
                grpcUser.getLogin(),
                roles
        );
    }

    private RoleDto toRoleDto(Role grpcRole) {
        return new RoleDto(
                grpcRole.getId().getValue(),
                grpcRole.getName(),
                grpcRole.getPermissionsList()
        );
    }
}
