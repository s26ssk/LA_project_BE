/**
 * Copyright(C) 2024  Luvina
 * CertificationDTO.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Lớp dữ liệu chuyển giao (DTO) cho thông tin chứng chỉ.
 * Sử dụng @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString.
 * Sử dụng @Builder để hỗ trợ xây dựng đối tượng theo mẫu Builder.
 */
@Data
@Builder
public class CertificationDTO implements Serializable {
    private static final long serialVersionUID = 1L; // Số phiên bản để đảm bảo khả năng tương thích trong quá trình tuần tự hóa

    private Long certificationId; // ID của chứng chỉ
    private String certificationName; // Tên của chứng chỉ
}
