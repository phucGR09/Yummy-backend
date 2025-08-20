package com.intro2se.yummy.exceptions;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    UNCATEGORIZED(9999, "Unknown error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_USERNAME(1001, "Username must be at least 2 characters and at most 50 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1002, "Password must be at least 8 characters and at most 20 characters", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(1003, "Email is required and must be at most 50 characters", HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_USED(1004, "Email is already used", HttpStatus.BAD_REQUEST),
    INVALID_FULLNAME(1005, "Full name is required and must be at most 50 characters ", HttpStatus.BAD_REQUEST),
    INVALID_PHONE_NUMBER(1006, "Phone number is required and must be at most 50 characters", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_USED(1007, "Phone number is already used", HttpStatus.BAD_REQUEST),
    USER_ALREADY_EXISTS(1008, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1009, "User not found", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1010, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    CUSTOMER_NOT_FOUND(1011, "Customer not found", HttpStatus.BAD_REQUEST),
    INVALID_USER_TYPE(1012, "Invalid user type", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_STATUS(1013, "Invalid order status", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_TYPE(1014, "Invalid order type", HttpStatus.BAD_REQUEST),
    INVALID_USER(1015, "Invalid user", HttpStatus.BAD_REQUEST),
    INVALID_AVATAR_URL(1016, "Invalid avatar url", HttpStatus.BAD_REQUEST),
    INVALID_LICENSE_PLATE(1017, "Invalid license plate", HttpStatus.BAD_REQUEST),
    INVALID_IDENTITY_NUMBER(1018, "Invalid identity number", HttpStatus.BAD_REQUEST),
    DELIVERY_DRIVER_NOT_FOUND(1019, "Delivery driver not found", HttpStatus.BAD_REQUEST),
    INVALID_TAX_NUMBER(1020, "Invalid tax number", HttpStatus.BAD_REQUEST),
    RESTAURANT_OWNER_NOT_FOUND(1021, "Restaurant not found", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1022, "Email not found", HttpStatus.BAD_REQUEST),
    RESTAURANT_NOT_FOUND(1023, "Restaurant not found", HttpStatus.BAD_REQUEST),
    INVALID_ID(1024, "Invalid id", HttpStatus.BAD_REQUEST),
    NULL_USER(1025, "Null user", HttpStatus.BAD_REQUEST),
    INVALID_USER_NAME(1026, "Invalid user name", HttpStatus.BAD_REQUEST),
    INVALID_RESTAURANT_NAME(1027, "Invalid restaurant name", HttpStatus.BAD_REQUEST),
    INVALID_PRICE(1028, "Invalid price", HttpStatus.BAD_REQUEST),
    INVALID_DESCRIPTION(1029, "Invalid description", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_URL(1030, "Invalid image url", HttpStatus.BAD_REQUEST),
    INVALID_RESTAURANT_ID(1031, "Invalid restaurant id", HttpStatus.BAD_REQUEST),
    EMPTY_USERNAME(1032, "Username cannot be empty", HttpStatus.BAD_REQUEST),
    EMPTY_PASSWORD(1034, "Password cannot be empty", HttpStatus.BAD_REQUEST),
    NULL_RESTAURANT(1035, "Null restaurant", HttpStatus.BAD_REQUEST),
    NULL_DRIVER(1036, "Null driver", HttpStatus.BAD_REQUEST),
    NULL_CUSTOMER(1037, "Null customer", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1038, "Order not found", HttpStatus.BAD_REQUEST),
    DRIVER_NOT_FOUND(1039, "User not found", HttpStatus.BAD_REQUEST),
    REASSIGN_DRIVER_IN_ORDER(1040, "Reassign driver in order", HttpStatus.BAD_REQUEST),
    NULL_ORDER(1041, "Null order", HttpStatus.BAD_REQUEST),
    NULL_MENU_ITEM(1042, "Null menu item", HttpStatus.BAD_REQUEST),
    MENU_ITEM_NOT_FOUND(1043, "Menu item not found", HttpStatus.BAD_REQUEST),
    ORDER_MENU_ITEM_NOT_FOUND(1044, "Order menu item not found", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS(1045, "Invalid address", HttpStatus.BAD_REQUEST),
    DISH_NAME_TOO_LONG(1046, "Dish name is too long", HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
