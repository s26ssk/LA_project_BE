/**
 * Copyright(C) 2024  Luvina
 * LoginRequest.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.payload;

import lombok.Data;

/**
 * Lớp LoginRequest được sử dụng để chứa dữ liệu yêu cầu khi người dùng
 * thực hiện đăng nhập. Nó bao gồm thông tin về tên đăng nhập và mật khẩu.
 */
@Data
public class LoginRequest {
    private String username; // Tên đăng nhập của người dùng
    private String password; // Mật khẩu của người dùng
}
