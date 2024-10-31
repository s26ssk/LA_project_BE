/**
 * Copyright(C) 2024  Luvina
 * DepartmentMapper.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper cho chuyển đổi giữa Department và DepartmentDTO.
 * Sử dụng @Mapper để chỉ định rằng đây là một mapper của MapStruct.
 * componentModel = "spring" cho phép sử dụng dependency injection từ Spring.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    // Tạo một instance của DepartmentMapper
    DepartmentMapper MAPPER = Mappers.getMapper(DepartmentMapper.class);

    /**
     * Chuyển đổi từ DepartmentDTO sang Department entity.
     */
    Department toEntity(DepartmentDTO dto);

    /**
     * Chuyển đổi từ Department entity sang DepartmentDTO.
     */
    DepartmentDTO toDto(Department entity);

    /**
     * Chuyển đổi danh sách Department entity sang danh sách DepartmentDTO.
     */
    List<DepartmentDTO> toList(List<Department> list);
}
