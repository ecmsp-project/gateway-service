package com.ecmsp.gatewayservice.api.rest.role;

import com.ecmsp.gatewayservice.api.grpc.role.RoleGrpcClient;
import com.ecmsp.gatewayservice.api.grpc.role.RoleGrpcMapper;
import com.ecmsp.gatewayservice.api.rest.UserContextWrapper;
import com.ecmsp.gatewayservice.api.rest.role.dto.AssignRoleRequestDto;
import com.ecmsp.gatewayservice.api.rest.role.dto.RoleToCreateDto;
import com.ecmsp.gatewayservice.api.rest.user.dto.RoleDto;
import com.ecmsp.user.v1.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleGrpcClient roleGrpcClient;
    private final RoleGrpcMapper roleGrpcMapper;

    public RoleController(RoleGrpcClient roleGrpcClient, RoleGrpcMapper roleGrpcMapper) {
        this.roleGrpcClient = roleGrpcClient;
        this.roleGrpcMapper = roleGrpcMapper;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleToCreateDto roleToCreateDto, HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        Role grpcRole = Role.newBuilder()
                .setName(roleToCreateDto.name())
                .addAllPermissions(roleToCreateDto.permissions())
                .build();

        CreateRoleResponse grpcResponse = roleGrpcClient.createRole(grpcRole, wrapper);
        RoleDto roleDto = roleGrpcMapper.toRoleDto(grpcResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(roleDto);
    }

    @PutMapping()
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto, HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;

        Role grpcRole = Role.newBuilder()
                .setName(roleDto.name())
                .addAllPermissions(roleDto.permissions())
                .build();

        UpdateRoleResponse grpcResponse = roleGrpcClient.updateRole(grpcRole, wrapper);
        RoleDto updatedRoleDto = roleGrpcMapper.toRoleDto(grpcResponse);

        return ResponseEntity.ok(updatedRoleDto);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleId, HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        roleGrpcClient.deleteRole(roleId, wrapper);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> listRoles(HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        ListRolesResponse grpcResponse = roleGrpcClient.listRoles(wrapper);
        List<RoleDto> roles = roleGrpcMapper.toRoleListDto(grpcResponse);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/{roleId}/users")
    public ResponseEntity<Void> assignRoleToUsers(@PathVariable String roleId, @RequestBody AssignRoleRequestDto assignRoleRequestDto, HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        roleGrpcClient.assignRoleToUsers(roleId, assignRoleRequestDto.userIds(), wrapper);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{roleId}/users")
    public ResponseEntity<Void> removeRoleFromUsers(@PathVariable String roleId, @RequestBody AssignRoleRequestDto assignRoleRequestDto, HttpServletRequest request) {
        UserContextWrapper wrapper = (UserContextWrapper) request;
        roleGrpcClient.removeRoleFromUsers(roleId, assignRoleRequestDto.userIds(), wrapper);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<String>> listAllPermissions(HttpServletRequest request) {
            UserContextWrapper wrapper = (UserContextWrapper) request;
            ListAllPermissionsResponse grpcResponse = roleGrpcClient.listAllPermissions(wrapper);
            List<String> permissions = roleGrpcMapper.toPermissionList(grpcResponse);
            return ResponseEntity.ok(permissions);
    }
}
