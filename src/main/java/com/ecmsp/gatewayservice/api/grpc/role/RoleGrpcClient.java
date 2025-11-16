package com.ecmsp.gatewayservice.api.grpc.role;

import com.ecmsp.gatewayservice.api.grpc.UserContextGrpcWrapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.user.v1.*;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleGrpcClient {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    private UserServiceGrpc.UserServiceBlockingStub stubWithMetadata(UserContextWrapper wrapper) {
        UserContextGrpcWrapper credentials = new UserContextGrpcWrapper(wrapper);
        return userServiceBlockingStub.withCallCredentials(credentials);
    }

    public CreateRoleResponse createRole(Role role, UserContextWrapper wrapper) {
        CreateRoleRequest request = CreateRoleRequest.newBuilder()
                .setRole(role)
                .build();
        return stubWithMetadata(wrapper).createRole(request);
    }

    public UpdateRoleResponse updateRole(Role role, UserContextWrapper wrapper) {
        UpdateRoleRequest request = UpdateRoleRequest.newBuilder()
                .setRole(role)
                .build();
        return stubWithMetadata(wrapper).updateRole(request);
    }

    public DeleteRoleResponse deleteRole(String roleId, UserContextWrapper wrapper) {
        DeleteRoleRequest request = DeleteRoleRequest.newBuilder()
                .setRoleId(roleId)
                .build();
        return stubWithMetadata(wrapper).deleteRole(request);
    }

    public ListRolesResponse listRoles(UserContextWrapper wrapper) {
        ListRolesRequest request = ListRolesRequest.newBuilder()
                .build();
        return stubWithMetadata(wrapper).listRoles(request);
    }

    public AssignRoleToUsersResponse assignRoleToUsers(String roleName, List<String> userIds, UserContextWrapper wrapper) {
        AssignRoleToUsersRequest request = AssignRoleToUsersRequest.newBuilder()
                .setRoleName(roleName)
                .addAllUserIds(userIds)
                .build();
        return stubWithMetadata(wrapper).assignRoleToUsers(request);
    }

    public RemoveRoleFromUsersResponse removeRoleFromUsers(String roleName, List<String> userIds, UserContextWrapper wrapper) {
        RemoveRoleFromUsersRequest request = RemoveRoleFromUsersRequest.newBuilder()
                .setRoleName(roleName)
                .addAllUserIds(userIds)
                .build();
        return stubWithMetadata(wrapper).removeRoleFromUsers(request);
    }

    public ListAllPermissionsResponse listAllPermissions(UserContextWrapper wrapper) {
        ListAllPermissionsRequest request = ListAllPermissionsRequest.newBuilder()
                .build();
        return stubWithMetadata(wrapper).listAllPermissions(request);
    }
}
