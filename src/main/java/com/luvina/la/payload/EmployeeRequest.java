/**
 * Copyright(C) 2024  Luvina
 * EmployeeRequest.java, 11/10/2024 KhanhNV
 */
package com.luvina.la.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * Lớp EmployeeRequest được sử dụng để nhận dữ liệu yêu cầu cho nhân viên.
 * Chứa thông tin về ID nhân viên, tên, ngày sinh, email, số điện thoại, tên Kana,
 * ID đăng nhập, mật khẩu, ID phòng ban và các chứng chỉ liên quan.
 */
@Data
public class EmployeeRequest {

    // ID của nhân viên
    private Long employeeId;

    // Tên của nhân viên
    private String employeeName;

    // Ngày sinh của nhân viên, định dạng "yyyy/MM/dd"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;

    // Địa chỉ email của nhân viên
    private String employeeEmail;

    // Số điện thoại của nhân viên
    private String employeeTelephone;

    // Tên Kana của nhân viên
    private String employeeNameKana;

    // ID đăng nhập của nhân viên
    private String employeeLoginId;

    // Mật khẩu đăng nhập của nhân viên
    private String employeeLoginPassword;

    // ID của phòng ban mà nhân viên thuộc về
    private Long departmentId;

    // Tập hợp các chứng chỉ liên quan đến nhân viên
    private Set<CertificationRequest> certifications;
}
