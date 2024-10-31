/**
 * Copyright(C) 2024  Luvina
 * DepartmentRepository.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DepartmentRepository là một interface mở rộng JpaRepository,
 * cung cấp các phương thức CRUD cho thực thể Department.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
