package com.luvina.la.payload;

import com.luvina.la.dto.EmployeeDTO;
import lombok.Data;

import java.util.List;

@Data
public class EmployeeResponseDTO {
    private String code;
    private int totalRecords;
    private List<EmployeeDTO> employees;
}
