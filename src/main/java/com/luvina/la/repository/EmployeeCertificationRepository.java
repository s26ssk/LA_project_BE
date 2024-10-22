package com.luvina.la.repository;

import com.luvina.la.entity.EmployeesCertification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeCertificationRepository extends JpaRepository<EmployeesCertification, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM EmployeesCertification ec WHERE ec.employee.employeeId = :employeeId")
    void deleteByEmployeeId(@Param("employeeId") Long employeeId);
}
