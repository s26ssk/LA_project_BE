/**
 * Copyright(C) 2024  Luvina
 * EmployeeResponseDTO.java, 13/10/2024 KhanhNV
 */
package com.luvina.la.payload;

import com.luvina.la.dto.EmployeeDTO;
import lombok.Data;

import java.util.List;

/**
 * Lớp EmployeeResponseDTO được sử dụng để chứa dữ liệu phản hồi cho yêu cầu
 * liên quan đến nhân viên. Nó bao gồm mã phản hồi, tổng số bản ghi và danh sách
 * các nhân viên.
 */
@Data
public class EmployeeResponseDTO {
    private String code;  // Mã phản hồi (ví dụ: mã trạng thái)
    private int totalRecords; // Tổng số bản ghi nhân viên
    private List<EmployeeDTO> employees; // Danh sách các nhân viên
}
