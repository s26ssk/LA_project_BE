/**
 * Copyright(C) 2024  Luvina
 * EmployeeMapper.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.mapper;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper cho chuyển đổi giữa Employee và EmployeeDTO.
 * Sử dụng @Mapper để chỉ định rằng đây là một mapper của MapStruct.
 * componentModel = "spring" cho phép sử dụng dependency injection từ Spring.
 *
 * Sử dụng:
 *  EmployeeMapper.MAPPER.toEntity(dto);
 *  EmployeeMapper.MAPPER.toList(list);
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper( EmployeeMapper.class );

    Employee toEntity(EmployeeDTO entity);
    Employee toDto(EmployeeDTO entity);
    List<EmployeeDTO> toList(Iterable<Employee> list);
}

