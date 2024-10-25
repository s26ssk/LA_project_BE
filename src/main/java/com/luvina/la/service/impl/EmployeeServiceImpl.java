package com.luvina.la.service.impl;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.entity.Certification;
import com.luvina.la.entity.Department;
import com.luvina.la.entity.Employee;
import com.luvina.la.entity.EmployeesCertification;
import com.luvina.la.payload.CertificationRequest;
import com.luvina.la.payload.EmployeeRequest;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeCertificationRepository;
import com.luvina.la.repository.EmployeeRepository;
import com.luvina.la.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private EmployeeCertificationRepository employeeCertificationRepository;

    @Override
    public List<EmployeeDTO> getEmployees(String employeeName, Long departmentId,
                                            String ordEmployeeName, String ordCertificationName,
                                            String ordEndDate, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset/limit, limit);
        List<EmployeeDTO> employees = employeeRepository.findEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, pageable);

        return employees;
    }

    @Override
    public Integer countTotalRecords(String employeeName, Long departmentId) {
        return employeeRepository.countTotalRecords(employeeName,departmentId);
    }
    @Override
    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
    @Transactional
    @Override
    public Long addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeBirthDate(employeeRequest.getEmployeeBirthDate());
        employee.setEmployeeEmail(employeeRequest.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeRequest.getEmployeeTelephone());
        employee.setEmployeeNameKana(employeeRequest.getEmployeeNameKana());
        employee.setEmployeeLoginId(employeeRequest.getEmployeeLoginId());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(employeeRequest.getEmployeeLoginPassword());
        employee.setEmployeeLoginPassword(encodedPassword);

        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElse(new Department());

        employee.setDepartment(department);
        Set<EmployeesCertification> certifications = new HashSet<>();
        for (CertificationRequest certRequest : employeeRequest.getCertifications()) {
            Certification certification = certificationRepository.findById(certRequest.getCertificationId())
                    .orElse(new Certification());

            EmployeesCertification employeesCertification = new EmployeesCertification();
            employeesCertification.setCertification(certification);
            employeesCertification.setStartDate(certRequest.getCertificationStartDate());
            employeesCertification.setEndDate(certRequest.getCertificationEndDate());
            employeesCertification.setScore(certRequest.getCertificationScore());
            employeesCertification.setEmployee(employee);
            certifications.add(employeesCertification);
        }
        employee.setEmployeesCertifications(certifications);
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getEmployeeId();
    }

    @Override
    public EmployeeDetailDTO getEmployeeDetail(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) return null;

        EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO();
        employeeDetailDTO.setEmployeeId(employee.getEmployeeId());
        employeeDetailDTO.setEmployeeName(employee.getEmployeeName());
        employeeDetailDTO.setEmployeeBirthDate(employee.getEmployeeBirthDate());
        employeeDetailDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
        employeeDetailDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        employeeDetailDTO.setEmployeeEmail(employee.getEmployeeEmail());
        employeeDetailDTO.setEmployeeTelephone(employee.getEmployeeTelephone());
        employeeDetailDTO.setEmployeeNameKana(employee.getEmployeeNameKana());
        employeeDetailDTO.setEmployeeLoginId(employee.getEmployeeLoginId());

        Set<EmployeeDetailDTO.CertificationDTO> certificationDTOs = employee.getEmployeesCertifications().stream()
                .map(empCert -> {
                    EmployeeDetailDTO.CertificationDTO certDTO = new EmployeeDetailDTO.CertificationDTO();
                    certDTO.setCertificationId(empCert.getCertification().getCertificationId());
                    certDTO.setCertificationName(empCert.getCertification().getCertificationName());
                    certDTO.setCertificationStartDate(empCert.getStartDate());
                    certDTO.setCertificationEndDate(empCert.getEndDate());
                    certDTO.setCertificationScore(empCert.getScore());
                    return certDTO;
                })
                .collect(Collectors.toSet());

        employeeDetailDTO.setCertifications(certificationDTOs);

        return employeeDetailDTO;
    }
    @Transactional
    @Override
    public boolean deleteEmployeeById(Long employeeId) {
        employeeCertificationRepository.deleteByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    @Transactional
    @Override
    public boolean updateEmployee(Long employeeId, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) return false;

        // Cập nhật thông tin của nhân viên
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeBirthDate(employeeRequest.getEmployeeBirthDate());
        employee.setEmployeeEmail(employeeRequest.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeRequest.getEmployeeTelephone());
        employee.setEmployeeNameKana(employeeRequest.getEmployeeNameKana());
        employee.setEmployeeLoginId(employeeRequest.getEmployeeLoginId());

        // Mã hóa mật khẩu nếu có
        if (employeeRequest.getEmployeeLoginPassword() != null && !employeeRequest.getEmployeeLoginPassword().isEmpty()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(employeeRequest.getEmployeeLoginPassword());
            employee.setEmployeeLoginPassword(encodedPassword);
        }

        // Cập nhật phòng ban
        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElse(new Department());
        employee.setDepartment(department);

        // Xóa các chứng chỉ cũ và thêm mới
        if (employee.getEmployeesCertifications() != null) {
            employeeCertificationRepository.deleteByEmployeeId(employeeId);
        }

        // Thêm các chứng chỉ mới nếu có
        if (employeeRequest.getCertifications() != null && !employeeRequest.getCertifications().isEmpty()) {
            employeeCertificationRepository.deleteByEmployeeId(employeeId);
            for (CertificationRequest certificationRequest : employeeRequest.getCertifications()) {
                Certification certification = certificationRepository.findById(certificationRequest.getCertificationId())
                        .orElse(null);
                if (certification != null) {
                    EmployeesCertification employeesCertification = new EmployeesCertification();
                    employeesCertification.setEmployee(employee);
                    employeesCertification.setCertification(certification);
                    employeesCertification.setStartDate(certificationRequest.getCertificationStartDate());
                    employeesCertification.setEndDate(certificationRequest.getCertificationEndDate());
                    employeesCertification.setScore(certificationRequest.getCertificationScore());
                    employee.getEmployeesCertifications().add(employeesCertification);
                }
            }
        }

        employeeRepository.save(employee);
        return true;
    }




}
