package com.cts.elearn.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String role;
    private String status;

    public LoginResponse(String token, String role, String status) {
        this.token = token;
        this.role = role;
        this.status = status;
    }
}
