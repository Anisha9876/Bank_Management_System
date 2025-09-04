package com.example.Bank_Anisha.dto;

import com.example.Bank_Anisha.Entity.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class API_Response<T>{

        private String status;
        private String message;
        private T data;
        private LocalDateTime timestamp;

        private long count;

    public API_Response(String status, T data, long count) {
        this.status = status;
        this.data = data;
        this.count = count;
    }

    public API_Response(String status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
            this.timestamp = LocalDateTime.now();
        }

    public API_Response(T account, String success) {
        this.data=account;
        this.status=success;
    }


    public static <T> API_Response<T> success(T data, long count) {
        API_Response<T> response = new API_Response<>("success", data, count);
        response.message = "Request Successful";
        response.timestamp = LocalDateTime.now();
        return response;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public long getCount() {
        return count;
    }
}
