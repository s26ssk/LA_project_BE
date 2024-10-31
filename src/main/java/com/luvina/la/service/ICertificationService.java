/**
 * Copyright(C) 2024  Luvina
 * ICertificationService.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.service;

import com.luvina.la.dto.CertificationDTO;

import java.util.List;

/**
 * Interface định nghĩa các phương thức liên quan đến chứng chỉ.
 */
public interface ICertificationService {
    /**
     * Lấy tất cả chứng chỉ từ cơ sở dữ liệu.
     * @return Danh sách các CertificationDTO.
     */
    List<CertificationDTO> getAllCertifications();
}
