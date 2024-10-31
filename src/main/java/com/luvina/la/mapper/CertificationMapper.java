/**
 * Copyright(C) 2024  Luvina
 * CertificationMapper.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper cho chuyển đổi giữa Certification và CertificationDTO.
 * Sử dụng @Mapper để chỉ định rằng đây là một mapper của MapStruct.
 * componentModel = "spring" cho phép sử dụng dependency injection từ Spring.
 */
@Mapper(componentModel = "spring")
public interface CertificationMapper {
    // Tạo một instance của CertificationMapper
    CertificationMapper MAPPER = Mappers.getMapper(CertificationMapper.class);

    /**
     * Chuyển đổi từ CertificationDTO sang Certification entity.
     */
    Certification toEntity(CertificationDTO dto);

    /**
     * Chuyển đổi từ Certification entity sang CertificationDTO.
     */
    CertificationDTO toDto(Certification entity);

    /**
     * Chuyển đổi danh sách Certification entity sang danh sách CertificationDTO.
     */
    List<CertificationDTO> toList(List<Certification> list);
}
