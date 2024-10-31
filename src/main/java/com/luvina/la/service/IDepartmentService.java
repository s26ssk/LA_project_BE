/**
 * Copyright(C) 2024  Luvina
 * IDepartmentService.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.service;

import com.luvina.la.dto.DepartmentDTO;

import java.util.List;

/**
 * Interface định nghĩa các phương thức liên quan đến phòng ban.
 */
public interface IDepartmentService {
    /**
     * Lấy tất cả phòng ban từ cơ sở dữ liệu.
     * @return Danh sách các DepartmentDTO.
     */
    List<DepartmentDTO> getAllDepartments();
}
