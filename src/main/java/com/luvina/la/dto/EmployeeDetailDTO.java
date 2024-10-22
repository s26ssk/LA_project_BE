package com.luvina.la.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class EmployeeDetailDTO {
    private String code;
    private Long employeeId;
    private String employeeName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date employeeBirthDate;
    private Long departmentId;
    private String departmentName;
    private String employeeEmail;
    private String employeeTelephone;
    private String employeeNameKana;
    private String employeeLoginId;
    private Set<CertificationDTO> certifications;

    @Data
    public static class CertificationDTO {
        private Long certificationId;
        private String certificationName;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        private Date startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        private Date endDate;
        private Double score;
    }
}
