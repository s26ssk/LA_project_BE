/**
 * Copyright(C) 2024  Luvina
 * EmployeeCertificationRepository.java, 21/10/2024 KhanhNV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.EmployeesCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * EmployeeCertificationRepository là một interface mở rộng JpaRepository,
 * cung cấp các phương thức CRUD cho thực thể EmployeesCertification.
 * Nó cũng cho phép thực hiện các truy vấn tùy chỉnh với @Query.
 */
public interface EmployeeCertificationRepository extends JpaRepository<EmployeesCertification, Long> {

    /**
     * Phương thức này xóa tất cả các bản ghi EmployeesCertification liên quan đến
     * một nhân viên cụ thể dựa trên employeeId.
     * @param employeeId ID của nhân viên cần xóa các chứng chỉ liên quan.
     */
    @Modifying // Đánh dấu phương thức này là một thao tác sửa đổi dữ liệu trong cơ sở dữ liệu.
    @Query("DELETE FROM EmployeesCertification ec WHERE ec.employee.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
