/**
 * Copyright(C) 2024  Luvina
 * ApiResponse.java, 10/10/2024 KhanhNV
 */
package com.luvina.la.payload;

import lombok.Data;

import java.util.List;

/**
 * Lớp ApiResponse được sử dụng để trả về phản hồi từ API.
 * Chứa thông tin về mã trạng thái, thông điệp và các tham số liên quan.
 */
@Data
public class ApiResponse {
    private String code; // Mã trạng thái phản hồi (ví dụ: "200" cho thành công, "500" cho lỗi)
    private String message; // Thông điệp mô tả phản hồi (thành công hay lỗi)
    private List<String> params;  // Danh sách các tham số bổ sung liên quan đến phản hồi

    /**
     * Constructor để khởi tạo đối tượng ApiResponse với các giá trị cụ thể.
     */
    public ApiResponse(String code, String message, List<String> params) {
        this.code = code;
        this.message = message;
        this.params = params;
    }
}
