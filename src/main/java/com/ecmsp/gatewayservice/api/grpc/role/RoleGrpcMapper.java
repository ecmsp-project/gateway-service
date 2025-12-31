package com.ecmsp.gatewayservice.api.grpc.role;

import com.ecmsp.gatewayservice.api.rest.user.dto.RoleDto;
import com.ecmsp.user.v1.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleGrpcMapper {

    public RoleDto toRoleDto(CreateRoleResponse grpcResponse) {
        return mapRole(grpcResponse.getRole());
    }

    public RoleDto toRoleDto(UpdateRoleResponse grpcResponse) {
        return mapRole(grpcResponse.getRole());
    }

    public List<RoleDto> toRoleListDto(ListRolesResponse grpcResponse) {
        return grpcResponse.getRolesList().stream()
                .map(this::mapRole)
                .toList();
    }

    public List<String> toPermissionList(ListAllPermissionsResponse grpcResponse) {
        return grpcResponse.getPermissionsList();
    }

    private RoleDto mapRole(Role grpcRole) {
        return new RoleDto(
                grpcRole.getName(),
                grpcRole.getPermissionsList()
        );
    }
}
