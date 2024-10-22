package com.luvina.la.service;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.payload.EmployeeRequest;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface IEmployeeService {
    List<EmployeeDTO> getEmployees(String employeeName, Long departmentId,
                      String ordEmployeeName, String ordCertificationName,
                      String ordEndDate, int offset, int limit);
    Integer countTotalRecords(String employeeName, Long departmentId);
    Long addEmployee(EmployeeRequest employeeRequest);
    EmployeeDetailDTO getEmployeeDetail(Long id);
    boolean deleteEmployeeById(Long employeeId);
    boolean existsById(Long employeeId);
}
