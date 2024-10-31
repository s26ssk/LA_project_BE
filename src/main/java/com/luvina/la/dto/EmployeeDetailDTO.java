/**
 * Copyright(C) 2024  Luvina
 * EmployeeDetailDTO.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * Lớp dữ liệu chuyển giao (DTO) cho thông tin chi tiết của nhân viên.
 * Sử dụng @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString.
 */
@Data
public class EmployeeDetailDTO {
    private String code; // Mã nhân viên
    private Long employeeId; // ID của nhân viên
    private String employeeName; // Tên của nhân viên

    // Ngày sinh của nhân viên, định dạng là yyyy/MM/dd
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;

    private Long departmentId; // ID của phòng ban
    private String departmentName; // Tên của phòng ban
    private String employeeEmail; // Địa chỉ email của nhân viên
    private String employeeTelephone; // Số điện thoại của nhân viên
    private String employeeNameKana; // Tên Kana của nhân viên
    private String employeeLoginId; // ID đăng nhập của nhân viên

    // Tập hợp các chứng chỉ của nhân viên
    private Set<CertificationDTO> certifications;

    /**
     * Lớp bên trong để mô tả chứng chỉ của nhân viên.
     * Sử dụng @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString.
     */
    @Data
    public static class CertificationDTO {
        private Long certificationId; // ID của chứng chỉ
        private String certificationName; // Tên của chứng chỉ

        // Ngày bắt đầu chứng chỉ, định dạng là yyyy/MM/dd
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        private Date certificationStartDate;

        // Ngày kết thúc chứng chỉ, định dạng là yyyy/MM/dd
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        private Date certificationEndDate;

        private Double certificationScore; // Điểm số của chứng chỉ
    }
}
