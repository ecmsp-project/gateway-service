package com.ecmsp.gatewayservice.api.grpc.user;

public enum PermissionsEnum {
    // Product permissions
    WRITE_PRODUCTS,
    DELETE_PRODUCTS,

    // Order permissions
    READ_ORDERS,
    WRITE_ORDERS,
    CANCEL_ORDERS,

    // User management permissions
    MANAGE_USERS,

    // Role management permissions
    MANAGE_ROLES,

    // Payment permissions
    PROCESS_PAYMENTS,
    REFUND_PAYMENTS

}
