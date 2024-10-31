/**
 * Copyright(C) 2024  Luvina
 * DepartmentDTO.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Lớp dữ liệu chuyển giao (DTO) cho thông tin phòng ban.
 * Sử dụng @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString.
 * Sử dụng @Builder để hỗ trợ xây dựng đối tượng theo mẫu Builder.
 */
@Data
@Builder
public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = -8120618322026608267L; // Số phiên bản để đảm bảo khả năng tương thích trong quá trình tuần tự hóa

    private Long departmentId; // ID của phòng ban
    private String departmentName; // Tên của phòng ban
}
