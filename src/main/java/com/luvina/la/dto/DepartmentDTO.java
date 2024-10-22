package com.luvina.la.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = -8120618322026608267L;

    private Long departmentId;
    private String departmentName;
}
