package com.ecmsp.gatewayservice.api.rest.order.dto;

//TODO: NEEDS TO BE UPDATED AS FOR NOW THERE IS MORE FIELDS - BUT FOR NOW THIS VERSION IS COMPATIBLE WITH THE SCHEMA DEFINITIONS
public record OrderItem(String itemId, int quantity) {
}
