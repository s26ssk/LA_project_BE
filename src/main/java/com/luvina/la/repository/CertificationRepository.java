/**
 * Copyright(C) 2024  Luvina
 * CertificationRepository.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.repository;

import com.luvina.la.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CertificationRepository là một interface mở rộng JpaRepository,
 * cung cấp các phương thức CRUD cho thực thể Certification.
 */
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
