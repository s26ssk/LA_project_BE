package com.luvina.la.mapper;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.entity.Certification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificationMapper {
    CertificationMapper MAPPER = Mappers.getMapper(CertificationMapper.class);

    Certification toEntity(CertificationDTO dto);
    CertificationDTO toDto(Certification entity);
    List<CertificationDTO> toList(List<Certification> list);
}
