package com.backend.backend.DTO;

public class ApiResponse<T> {

        private boolean status;
        private String message;
        private T data; // puede ser null

        public ApiResponse(boolean status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public static <T> ApiResponse<T> success(String message, T data) {
            return new ApiResponse<>(true, message, data);
        }

        public static <T> ApiResponse<T> success(String message) {
            return new ApiResponse<>(true, message, null);
        }

        public static <T> ApiResponse<T> error(String message) {
            return new ApiResponse<>(false, message, null);
        }

        public boolean isStatus() { return status; }
        public String getMessage() { return message; }
        public T getData() { return data; }
}
