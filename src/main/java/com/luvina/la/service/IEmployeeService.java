/**
 * Copyright(C) 2024  Luvina
 * IEmployeeService.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.service;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.payload.EmployeeRequest;

import java.util.List;

/**
 * Interface định nghĩa các phương thức liên quan đến nhân viên.
 */
public interface IEmployeeService {

    /**
     * Lấy danh sách nhân viên theo các tiêu chí tìm kiếm.
     */
    List<EmployeeDTO> getEmployees(String employeeName, Long departmentId,
                                   String ordEmployeeName, String ordCertificationName,
                                   String ordEndDate, int offset, int limit);

    /**
     * Đếm tổng số bản ghi nhân viên theo các tiêu chí tìm kiếm.
     */
    Integer countTotalRecords(String employeeName, Long departmentId);

    /**
     * Thêm một nhân viên mới vào cơ sở dữ liệu.
     */
    Long addEmployee(EmployeeRequest employeeRequest);

    /**
     * Lấy chi tiết thông tin của một nhân viên theo ID.
     */
    EmployeeDetailDTO getEmployeeDetail(Long id);

    /**
     * Xóa một nhân viên theo ID.
     */
    boolean deleteEmployeeById(Long employeeId);

    /**
     * Kiểm tra xem một nhân viên có tồn tại hay không theo ID.
     */
    boolean existsById(Long employeeId);

    /**
     * Cập nhật thông tin một nhân viên theo ID.
     */
    boolean updateEmployee(EmployeeRequest employeeRequest);
}
