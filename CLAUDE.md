# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the Gateway Service for the ECMSP (E-Commerce Microservices Platform) project. It acts as an API gateway that routes requests to backend microservices and handles JWT-based authentication.

**Tech Stack:**
- Java 21
- Spring Boot 3.5.6
- Maven
- gRPC (net.devh:grpc-spring-boot-starter)
- JWT (io.jsonwebtoken:jjwt)
- Lombok

## Architecture

### Gateway Pattern
The service implements the API Gateway pattern, providing:
- Single entry point for REST clients
- JWT authentication and authorization
- Request routing to backend microservices
- User context propagation via HTTP headers

### Backend Microservices
Configured service URLs (see `application.properties`):
- Cart Service: `http://localhost:8100`
- Payment Service: `http://localhost:8200`
- Order Service: `http://localhost:8300`
- Product Service: `http://localhost:8400`
- User Service: `http://localhost:8500`
- Gateway Service: `http://localhost:8600`

### JWT Security Architecture
1. **JwtTokenReader** (`application/security/JwtTokenReader.java`): Loads RSA public key from `classpath:keys/local.public.key` and validates JWT tokens
2. **JwtAuthenticationFilter** (`application/security/JwtAuthenticationFilter.java`): Servlet filter that intercepts requests, validates Bearer tokens, and wraps request with user context
3. **UserContextWrapper** (`application/security/UserContextWrapper.java`): HttpServletRequest wrapper that carries authenticated user's ID and login
4. **JwtConfig** (`application/config/JwtConfig.java`): Registers JWT filter for `/api/orders/*` endpoints

**Authentication Flow:**
- Public endpoints (e.g., `/api/auth/login`) bypass JWT filter
- Protected endpoints require `Authorization: Bearer <token>` header
- Filter extracts JWT claims (userId, login) and wraps request
- Controllers access user context via `UserContextWrapper`

### Service Clients
REST clients communicate with backend services using `RestTemplate`:
- **AuthClient**: Authenticates users via User Service
- **OrderServiceClient**: Proxies order requests with user context headers (`X-User-ID`, `X-Login`)

Pattern: Service clients inject `RestTemplate` bean and service URL from properties, then forward requests with additional headers/context.

## Commands

### Build
```bash
mvn clean install
```

### Run Application
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Run Single Test
```bash
mvn test -Dtest=ClassName#methodName
```

### Package
```bash
mvn package
```

## Development Notes

### JWT Public Key Setup
The application requires an RSA public key at `src/main/resources/keys/local.public.key` (PEM format). This key must match the private key used by the User Service to sign JWTs.

### Adding Protected Endpoints
To protect new endpoints with JWT authentication:
1. Add URL pattern to `JwtConfig.addUrlPatterns()` (line 16)
2. Cast `HttpServletRequest` to `UserContextWrapper` in controller
3. Access user context via `wrapper.getUserId()` and `wrapper.getLogin()`

### Adding New Service Clients
1. Add service URL to `application.properties` under `services.*`
2. Create client class annotated with `@Component`
3. Inject `RestTemplate` and `@Value("${services.your-service.url}")`
4. Use `restTemplate.exchange()` or similar methods for HTTP calls

### Maven Repository
Uses custom Nexus repository at `nexus.ecmsp.pl` for internal dependencies (e.g., `com.ecmsp:protos:1.0.0-SNAPSHOT`).

## Project Structure

```
src/main/java/com/ecmsp/gatewayservice/
├── api/rest/                      # REST API layer
│   ├── auth/                      # Authentication endpoints
│   │   ├── AuthController.java   # POST /api/auth/login
│   │   └── AuthClient.java       # User service client
│   └── order/                     # Order endpoints
│       ├── OrderController.java  # GET /api/orders/me (protected)
│       └── OrderServiceClient.java
├── application/
│   ├── security/                  # JWT authentication
│   │   ├── JwtAuthenticationFilter.java
│   │   ├── JwtTokenReader.java
│   │   └── UserContextWrapper.java
│   └── config/                    # Spring configuration
│       ├── JwtConfig.java        # JWT filter registration
│       └── RestTemplateConfig.java
└── GatewayServiceApplication.java
```

#GRPC Protobufs schemas
user_service.proto
```protobuf
syntax = "proto3";

import "user/v1/user.proto";
package user.v1;

option java_multiple_files = true;
option java_package = "com.ecmsp.user.v1";
option java_outer_classname = "UserServiceProto";

service UserService {
  rpc GetUser(GetUserRequest) returns (GetUserResponse);
  rpc CreateUser(CreateUserRequest) returns (CreateUserResponse);
  rpc UpdateUser(UpdateUserRequest) returns (UpdateUserResponse);
  rpc DeleteUser(DeleteUserRequest) returns (DeleteUserResponse);
  rpc ListUsers(ListUsersRequest) returns (ListUsersResponse);

  rpc CreateRole(CreateRoleRequest) returns (CreateRoleResponse);
  rpc UpdateRole(UpdateRoleRequest) returns (UpdateRoleResponse);
  rpc DeleteRole(DeleteRoleRequest) returns (DeleteRoleResponse);
  rpc ListRoles(ListRolesRequest) returns (ListRolesResponse);
  rpc AssignRoleToUsers(AssignRoleToUsersRequest) returns (AssignRoleToUsersResponse);
  rpc RemoveRoleFromUsers(RemoveRoleFromUsersRequest) returns (RemoveRoleFromUsersResponse);
  rpc ListAllPermissions(ListAllPermissionsRequest) returns (ListAllPermissionsResponse);
}

message GetUserRequest {
  string user_id = 1;
}

message GetUserResponse {
  User user = 1;
}

message CreateUserRequest {
  UserToCreate user_to_create = 1;
}

message CreateUserResponse {
  User user = 1;
}

message UpdateUserRequest {
  User user = 1;
}

message UpdateUserResponse {
  User user = 1;
}

message DeleteUserRequest {
  string user_id = 1;
}

message DeleteUserResponse {
}

message ListUsersRequest {
  string filter_login = 1;
  repeated User users = 2;
}

message ListUsersResponse {
  repeated User users = 1;
}

message CreateRoleRequest {
  Role role = 1;
}

message CreateRoleResponse {
  Role role = 1;
}

message UpdateRoleRequest {
  Role role = 1;
}

message UpdateRoleResponse {
  Role role = 1;
}

message DeleteRoleRequest {
  string role_id = 1;
}

message DeleteRoleResponse {
}

message ListRolesRequest {
}

message ListRolesResponse {
  repeated Role roles = 1;
}

message AssignRoleToUsersRequest {
  string role_id = 1;
  repeated string user_ids = 2;
}

message AssignRoleToUsersResponse {
}
message RemoveRoleFromUsersRequest {
  string role_id = 1;
  repeated string user_ids = 2;
}

message RemoveRoleFromUsersResponse {

}
message ListAllPermissionsRequest {

}

message ListAllPermissionsResponse {
  repeated string permissions = 1;
}
```

user.proto
```protobuf
syntax = "proto3";

package user.v1;

option java_multiple_files = true;
option java_package = "com.ecmsp.user.v1";
option java_outer_classname = "UserProto";

message UserId {
  string value = 1;
}

message RoleId {
  string value = 1;
}

message Role {
  RoleId id = 1;
  string name = 2;
  repeated string permissions = 3;
}

message User {
  UserId id = 1;
  string login = 2;
  repeated Role roles = 3;
}

message UserToCreate {
  string login = 1;
  string password = 2;
}
```
