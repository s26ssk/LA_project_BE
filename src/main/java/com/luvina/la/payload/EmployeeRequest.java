package com.luvina.la.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class EmployeeRequest {
    private String employeeName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeNameKana;
    private String employeeLoginId;
    private String employeeLoginPassword;
    private Long departmentId;
    private Set<CertificationRequest> certifications;
}
