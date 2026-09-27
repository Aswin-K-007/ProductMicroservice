package com.wishit.product.exception;

import java.util.Map;
import org.springframework.http.HttpStatus;

public class ErrorCodes {

    public static final int PRODUCT_NOT_FOUND = 1001;
    public static final int INVALID_OPERATION = 1002;
    public static final int INVALID_QUANTITY = 1003;

    public static final int INTERNAL_ERROR = 1004;
    public static final int FILE_UPLOAD_ERROR = 1005;
    public static final int PRODUCT_CREATION_ERROR = 1006;
    public static final int PRODUCT_FETCH_ERROR = 1007;

    private static final Map<Integer, String> ERROR_MESSAGES = Map.of(
            PRODUCT_NOT_FOUND,
                "Product not found",

            INVALID_OPERATION,
                "Operation must be '+' or '-'",

            INVALID_QUANTITY,
                "Stock quantity cannot be negative",

            INTERNAL_ERROR,
                "Something went wrong",

            FILE_UPLOAD_ERROR,
                "Failed to upload product image",

            PRODUCT_CREATION_ERROR,
                "Failed to create product",

            PRODUCT_FETCH_ERROR,
                "Failed to fetch products"
    );

    private static final Map<Integer, HttpStatus> ERROR_STATUS = Map.of(
            PRODUCT_NOT_FOUND,
                HttpStatus.NOT_FOUND,

            INVALID_OPERATION,
                HttpStatus.BAD_REQUEST,

            INVALID_QUANTITY,
                HttpStatus.BAD_REQUEST,

            INTERNAL_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,

            FILE_UPLOAD_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,

            PRODUCT_CREATION_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,

            PRODUCT_FETCH_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR
    );

    public static String getMessage(int errorNo) {
        return ERROR_MESSAGES.getOrDefault(
                errorNo,
                "Something went wrong");
    }

    public static HttpStatus getStatus(int errorNo) {
        return ERROR_STATUS.getOrDefault(
                errorNo,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorCodes() {
    }
}