/**
 * Copyright(C) 2024  Luvina
 * EmployeeRepository.java, 10/10/2024 KhanhNV
 */
package com.luvina.la.repository;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * EmployeeRepository là một interface mở rộng JpaRepository,
 * cung cấp các phương thức CRUD cho thực thể Employee.
 * Nó cũng cho phép thực hiện các truy vấn tùy chỉnh với @Query.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Tìm nhân viên theo login ID.
     */
    Optional<Employee> findByEmployeeLoginId(String employeeLoginId);

    /**
     * Tìm nhân viên theo ID.
     */
    Optional<Employee> findByEmployeeId(Long employeeId);

    /**
     * Tìm danh sách nhân viên theo các tiêu chí lọc và phân trang.
     */

    @Query("SELECT new com.luvina.la.dto.EmployeeDTO(" +
            "e.employeeId, " +
            "e.employeeName, " +
            "e.employeeBirthDate, " +
            "d.departmentName, " +
            "e.employeeEmail, " +
            "e.employeeTelephone, " +
            "c.certificationName, " +
            "ec.endDate, " +
            "ec.score) " +
            "FROM Employee e " +
            "JOIN e.department d " +
            "LEFT JOIN e.employeesCertifications ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:departmentId IS NULL OR e.department.departmentId = :departmentId) " +
            "AND (:employeeName IS NULL OR e.employeeName LIKE CONCAT('%', :employeeName, '%')) " +
            "ORDER BY " +
            "CASE WHEN :ordEmployeeName IS NOT NULL AND :ordEmployeeName = 'ASC' THEN e.employeeName END ASC, " +
            "CASE WHEN :ordEmployeeName IS NOT NULL AND :ordEmployeeName = 'DESC' THEN e.employeeName END DESC, " +
            "CASE WHEN :ordCertificationName IS NOT NULL AND :ordCertificationName = 'ASC' THEN c.certificationName END ASC, " +
            "CASE WHEN :ordCertificationName IS NOT NULL AND :ordCertificationName = 'DESC' THEN c.certificationName END DESC, " +
            "CASE WHEN :ordEndDate IS NOT NULL AND :ordEndDate = 'ASC' THEN ec.endDate END ASC, " +
            "CASE WHEN :ordEndDate IS NOT NULL AND :ordEndDate = 'DESC' THEN ec.endDate END DESC, " +
            "e.employeeId ASC")
    List<EmployeeDTO> findEmployees(@Param("employeeName") String employeeName,
                                    @Param("departmentId") Long departmentId,
                                    @Param("ordEmployeeName") String ordEmployeeName,
                                    @Param("ordCertificationName") String ordCertificationName,
                                    @Param("ordEndDate") String ordEndDate,
                                    Pageable pageable);

    /**
     * Đếm tổng số bản ghi nhân viên theo các tiêu chí lọc.
     */
    @Query("SELECT COUNT(e) FROM Employee e " +
            "JOIN e.department d " +
            "LEFT JOIN e.employeesCertifications ec " +
            "LEFT JOIN ec.certification c " +
            "WHERE (:departmentId IS NULL OR e.department.departmentId = :departmentId) " +
            "AND (:employeeName IS NULL OR e.employeeName LIKE CONCAT('%', :employeeName, '%'))")
    Integer countTotalRecords(@Param("employeeName") String employeeName,
                              @Param("departmentId") Long departmentId);

    /**
     * Kiểm tra xem một nhân viên có tồn tại dựa trên login ID hay không.
     */
    boolean existsByEmployeeLoginId(String employeeLoginId);
}
