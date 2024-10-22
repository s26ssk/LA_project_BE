package com.luvina.la.mapper;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentMapper MAPPER = Mappers.getMapper(DepartmentMapper.class);

    Department toEntity(DepartmentDTO dto);
    DepartmentDTO toDto(Department entity);
    List<DepartmentDTO> toList(List<Department> list);
}
