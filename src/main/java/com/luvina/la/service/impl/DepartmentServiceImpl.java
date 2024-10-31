/**
 * Copyright(C) 2024  Luvina
 * DepartmentServiceImpl.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.service.impl;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import com.luvina.la.mapper.DepartmentMapper;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DepartmentServiceImpl là lớp thực hiện các phương thức trong interface IDepartmentService.
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * Lấy tất cả các phòng ban từ cơ sở dữ liệu.
     * @return Danh sách các DepartmentDTO.
     */
    @Override
    public List<DepartmentDTO> getAllDepartments() {
        // Lấy danh sách các phòng ban từ repository
        List<Department> departments = departmentRepository.findAll();
        // Chuyển đổi danh sách phòng ban sang danh sách DTO và trả về
        return departmentMapper.toList(departments);
    }
}
