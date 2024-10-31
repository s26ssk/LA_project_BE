/**
 * Copyright(C) 2024  Luvina
 * EmployeeDTO.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * Lớp dữ liệu chuyển giao (DTO) cho thông tin của nhân viên.
 * Sử dụng @Data để tự động tạo các phương thức getter, setter, equals, hashCode và toString.
 * Sử dụng @Builder để hỗ trợ xây dựng đối tượng dễ dàng.
 */
@Data
@Builder
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = -4633466951324855248L; // Serial version UID cho tính tương thích

    private Long employeeId; // ID của nhân viên
    private String employeeName; // Tên của nhân viên
    private Date employeeBirthDate; // Ngày sinh của nhân viên
    private String departmentName; // Tên của phòng ban mà nhân viên thuộc về
    private String employeeEmail; // Địa chỉ email của nhân viên
    private String employeeTelephone; // Số điện thoại của nhân viên
    private String certificationName; // Tên của chứng chỉ mà nhân viên có
    private Date endDate; // Ngày kết thúc chứng chỉ
    private Double score; // Điểm số liên quan đến chứng chỉ

    /**
     * Constructor để khởi tạo đối tượng EmployeeDTO.
     */
    public EmployeeDTO(Long employeeId, String employeeName, Date employeeBirthDate,
                       String departmentName, String employeeEmail, String employeeTelephone,
                       String certificationName, Date endDate, Double score) {
        this.employeeId = employeeId; // Khởi tạo ID của nhân viên
        this.employeeName = employeeName; // Khởi tạo tên của nhân viên
        this.employeeBirthDate = employeeBirthDate; // Khởi tạo ngày sinh của nhân viên
        this.departmentName = departmentName; // Khởi tạo tên phòng ban
        this.employeeEmail = employeeEmail; // Khởi tạo địa chỉ email
        this.employeeTelephone = employeeTelephone; // Khởi tạo số điện thoại
        this.certificationName = certificationName; // Khởi tạo tên chứng chỉ
        this.endDate = endDate; // Khởi tạo ngày kết thúc chứng chỉ
        this.score = score; // Khởi tạo điểm số
    }
}
