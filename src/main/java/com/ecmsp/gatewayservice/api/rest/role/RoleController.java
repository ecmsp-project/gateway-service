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
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;

            Role grpcRole = Role.newBuilder()
                    .setName(roleToCreateDto.name())
                    .addAllPermissions(roleToCreateDto.permissions())
                    .build();

            CreateRoleResponse grpcResponse = roleGrpcClient.createRole(grpcRole, wrapper);
            RoleDto roleDto = roleGrpcMapper.toRoleDto(grpcResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(roleDto);
        } catch (Exception e) {
            log.error("Exception from createRole: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto, HttpServletRequest request) {
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;

            Role grpcRole = Role.newBuilder()
                    .setName(roleDto.name())
                    .addAllPermissions(roleDto.permissions())
                    .build();

            UpdateRoleResponse grpcResponse = roleGrpcClient.updateRole(grpcRole, wrapper);
            RoleDto updatedRoleDto = roleGrpcMapper.toRoleDto(grpcResponse);

            return ResponseEntity.ok(updatedRoleDto);
        } catch (Exception e) {
            log.error("Exception from updateRole: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable String roleId, HttpServletRequest request) {
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;
            roleGrpcClient.deleteRole(roleId, wrapper);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Exception from deleteRole: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<RoleDto>> listRoles(HttpServletRequest request) {
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;
            ListRolesResponse grpcResponse = roleGrpcClient.listRoles(wrapper);
            List<RoleDto> roles = roleGrpcMapper.toRoleListDto(grpcResponse);
            return ResponseEntity.ok(roles);
        } catch (Exception e) {
            log.error("Exception from listRoles: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{roleId}/users")
    public ResponseEntity<Void> assignRoleToUsers(@PathVariable String roleId, @RequestBody AssignRoleRequestDto assignRoleRequestDto, HttpServletRequest request) {
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;
            roleGrpcClient.assignRoleToUsers(roleId, assignRoleRequestDto.userIds(), wrapper);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Exception from assignRoleToUsers: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{roleId}/users")
    public ResponseEntity<Void> removeRoleFromUsers(@PathVariable String roleId, @RequestBody AssignRoleRequestDto assignRoleRequestDto, HttpServletRequest request) {
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;
            roleGrpcClient.removeRoleFromUsers(roleId, assignRoleRequestDto.userIds(), wrapper);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Exception from removeRoleFromUsers: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<String>> listAllPermissions(HttpServletRequest request) {
        try {
            UserContextWrapper wrapper = (UserContextWrapper) request;
            ListAllPermissionsResponse grpcResponse = roleGrpcClient.listAllPermissions(wrapper);
            List<String> permissions = roleGrpcMapper.toPermissionList(grpcResponse);
            return ResponseEntity.ok(permissions);
        } catch (Exception e) {
            log.error("Exception from listAllPermissions: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
