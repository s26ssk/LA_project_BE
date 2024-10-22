package com.luvina.la.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = -4633466951324855248L;

    private Long employeeId;
    private String employeeName;
    private Date employeeBirthDate;
    private String departmentName;
    private String employeeEmail;
    private String employeeTelephone;
    private String certificationName;
    private Date endDate;
    private Double score;

    public EmployeeDTO(Long employeeId, String employeeName, Date employeeBirthDate,
                       String departmentName, String employeeEmail, String employeeTelephone,
                       String certificationName, Date endDate, Double score) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeBirthDate = employeeBirthDate;
        this.departmentName = departmentName;
        this.employeeEmail = employeeEmail;
        this.employeeTelephone = employeeTelephone;
        this.certificationName = certificationName;
        this.endDate = endDate;
        this.score = score;
    }
}
