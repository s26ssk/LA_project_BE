package com.luvina.la.service.impl;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.mapper.CertificationMapper;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.service.ICertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements ICertificationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private CertificationMapper certificationMapper;

    @Override
    public List<CertificationDTO> getAllCertifications() {
        List<Certification> certifications = certificationRepository.findAll();
        return certificationMapper.toList(certifications);
    }
}
