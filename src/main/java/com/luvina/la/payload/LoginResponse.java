/**
 * Copyright(C) 2024  Luvina
 * LoginResponse.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.payload;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Lớp LoginResponse được sử dụng để chứa dữ liệu phản hồi khi người dùng
 * thực hiện đăng nhập. Nó có thể chứa token truy cập, loại token và các lỗi (nếu có).
 */
@Data
public class LoginResponse {
    private String accessToken; // Token truy cập được cấp cho người dùng sau khi đăng nhập thành công
    private String tokenType; // Loại token, thường là "Bearer"
    private Map<String, String> errors = new HashMap<>(); // Map chứa các lỗi nếu có trong quá trình đăng nhập

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }
    public LoginResponse(Map<String, String> errors) {
        this.errors = errors;
    }
}
