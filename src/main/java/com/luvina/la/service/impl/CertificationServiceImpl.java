/**
 * Copyright(C) 2024  Luvina
 * CertificationServiceImpl.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.service.impl;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.ICertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CertificationServiceImpl là lớp thực hiện các phương thức trong interface ICertificationService.
 */
@Service
public class CertificationServiceImpl implements ICertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationMapper certificationMapper;

    /**
     * Lấy tất cả chứng chỉ từ cơ sở dữ liệu.
     * @return Danh sách các CertificationDTO.
     */
    @Override
    public List<CertificationDTO> getAllCertifications() {
        // Lấy danh sách các chứng chỉ từ repository
        List<Certification> certifications = certificationRepository.findAll();
        // Chuyển đổi danh sách chứng chỉ sang danh sách DTO và trả về
        return certificationMapper.toList(certifications);
    }
}
