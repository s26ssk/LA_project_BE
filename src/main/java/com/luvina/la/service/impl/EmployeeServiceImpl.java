/**
 * Copyright(C) 2024  Luvina
 * EmployeeServiceImpl.java, 04/10/2024 KhanhNV
 */
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

/**
 * EmployeeServiceImpl là lớp thực hiện các phương thức trong interface IEmployeeService.
 */
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

    /**
     * Lấy danh sách nhân viên theo các tiêu chí tìm kiếm và phân trang.
     * @param employeeName Tên nhân viên để tìm kiếm.
     * @param departmentId ID của phòng ban.
     * @param ordEmployeeName Phương thức sắp xếp theo tên nhân viên.
     * @param ordCertificationName Phương thức sắp xếp theo tên chứng chỉ.
     * @param ordEndDate Phương thức sắp xếp theo ngày kết thúc chứng chỉ.
     * @param offset Vị trí bắt đầu phân trang.
     * @param limit Số lượng bản ghi trên một trang.
     * @return Danh sách EmployeeDTO.
     */
    @Override
    public List<EmployeeDTO> getEmployees(String employeeName, Long departmentId,
                                          String ordEmployeeName, String ordCertificationName,
                                          String ordEndDate, int offset, int limit) {
        // Tạo đối tượng Pageable để phân trang
        Pageable pageable = PageRequest.of(offset / limit, limit); // Tính toán trang và số bản ghi mỗi trang

        // Gọi phương thức trong repository để lấy danh sách nhân viên theo tiêu chí tìm kiếm
        List<EmployeeDTO> employees = employeeRepository.findEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, pageable);

        // Trả về danh sách EmployeeDTO
        return employees;
    }


    /**
     * Đếm tổng số bản ghi nhân viên theo các tiêu chí tìm kiếm.
     * @param employeeName Tên nhân viên để tìm kiếm.
     * @param departmentId ID của phòng ban.
     * @return Tổng số bản ghi.
     */
    @Override
    public Integer countTotalRecords(String employeeName, Long departmentId) {
        return employeeRepository.countTotalRecords(employeeName, departmentId);
    }

    /**
     * Kiểm tra xem nhân viên có tồn tại trong cơ sở dữ liệu hay không.
     * @param employeeId ID của nhân viên.
     * @return true nếu tồn tại, ngược lại false.
     */
    @Override
    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }

    /**
     * Thêm một nhân viên mới vào cơ sở dữ liệu.
     * @param employeeRequest Thông tin của nhân viên cần thêm.
     * @return ID của nhân viên vừa thêm.
     */
    @Transactional
    @Override
    public Long addEmployee(EmployeeRequest employeeRequest) {
        // Tạo mới đối tượng Employee và gán lại các thông tin cho employee
        Employee employee = new Employee();
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeBirthDate(employeeRequest.getEmployeeBirthDate());
        employee.setEmployeeEmail(employeeRequest.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeRequest.getEmployeeTelephone());
        employee.setEmployeeNameKana(employeeRequest.getEmployeeNameKana());
        employee.setEmployeeLoginId(employeeRequest.getEmployeeLoginId());

        // Mã hóa mật khẩu
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Khởi tạo encoder
        String encodedPassword = passwordEncoder.encode(employeeRequest.getEmployeeLoginPassword()); // Mã hóa mật khẩu
        employee.setEmployeeLoginPassword(encodedPassword); // Cập nhật mật khẩu đã mã hóa

        // Tìm kiếm và thiết lập phòng ban
        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElse(new Department()); // Nếu không tìm thấy thì tạo mới phòng ban
        employee.setDepartment(department); // Thiết lập phòng ban cho nhân viên

        // Tạo và thêm các chứng chỉ cho nhân viên
        Set<EmployeesCertification> certifications = new HashSet<>();
        for (CertificationRequest certRequest : employeeRequest.getCertifications()) {
            Certification certification = certificationRepository.findById(certRequest.getCertificationId())
                    .orElse(new Certification()); // Tìm kiếm chứng chỉ

            EmployeesCertification employeesCertification = new EmployeesCertification();
            employeesCertification.setCertification(certification);
            employeesCertification.setStartDate(certRequest.getCertificationStartDate());
            employeesCertification.setEndDate(certRequest.getCertificationEndDate());
            employeesCertification.setScore(certRequest.getCertificationScore());
            employeesCertification.setEmployee(employee);
            certifications.add(employeesCertification);
        }
        employee.setEmployeesCertifications(certifications);

        // Lưu nhân viên vào cơ sở dữ liệu
        Employee savedEmployee = employeeRepository.save(employee);
        return savedEmployee.getEmployeeId();
    }


    /**
     * Lấy chi tiết thông tin của một nhân viên theo ID.
     * @param id ID của nhân viên.
     * @return EmployeeDetailDTO chứa thông tin chi tiết của nhân viên.
     */
    @Override
    public EmployeeDetailDTO getEmployeeDetail(Long id) {
        // Tìm kiếm nhân viên theo ID, trả về null nếu không tìm thấy
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) return null; // Nếu không có nhân viên thì trả về null

        EmployeeDetailDTO employeeDetailDTO = new EmployeeDetailDTO(); // Tạo DTO để chứa thông tin chi tiết
        // Thiết lập các thông tin của nhân viên
        employeeDetailDTO.setEmployeeId(employee.getEmployeeId());
        employeeDetailDTO.setEmployeeName(employee.getEmployeeName());
        employeeDetailDTO.setEmployeeBirthDate(employee.getEmployeeBirthDate());
        employeeDetailDTO.setDepartmentId(employee.getDepartment().getDepartmentId());
        employeeDetailDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        employeeDetailDTO.setEmployeeEmail(employee.getEmployeeEmail());
        employeeDetailDTO.setEmployeeTelephone(employee.getEmployeeTelephone());
        employeeDetailDTO.setEmployeeNameKana(employee.getEmployeeNameKana());
        employeeDetailDTO.setEmployeeLoginId(employee.getEmployeeLoginId());

        Set<EmployeeDetailDTO.CertificationDTO> certificationDTOs = new HashSet<>(); // Khởi tạo tập hợp chứng chỉ
        // Duyệt qua các chứng chỉ của nhân viên và thiết lập thông tin
        for (EmployeesCertification empCert : employee.getEmployeesCertifications()) {
            EmployeeDetailDTO.CertificationDTO certDTO = new EmployeeDetailDTO.CertificationDTO();
            certDTO.setCertificationId(empCert.getCertification().getCertificationId());
            certDTO.setCertificationName(empCert.getCertification().getCertificationName());
            certDTO.setCertificationStartDate(empCert.getStartDate());
            certDTO.setCertificationEndDate(empCert.getEndDate());
            certDTO.setCertificationScore(empCert.getScore());
            certificationDTOs.add(certDTO); // Thêm chứng chỉ vào tập hợp
        }

        employeeDetailDTO.setCertifications(certificationDTOs); // Thiết lập danh sách chứng chỉ cho DTO
        return employeeDetailDTO;
    }


    /**
     * Xóa một nhân viên theo ID.
     * @param employeeId ID của nhân viên.
     * @return true nếu xóa thành công, ngược lại false.
     */
    @Transactional
    @Override
    public boolean deleteEmployeeById(Long employeeId) {
        employeeCertificationRepository.deleteByEmployeeId(employeeId);
        employeeRepository.deleteById(employeeId);
        return true;
    }

    /**
     * Cập nhật thông tin của một nhân viên.
     * @param employeeRequest Thông tin mới của nhân viên.
     * @return true nếu cập nhật thành công, ngược lại false.
     */
    @Transactional
    @Override
    public boolean updateEmployee(EmployeeRequest employeeRequest) {
        // Tìm kiếm nhân viên theo ID, trả về false nếu không tìm thấy
        Employee employee = employeeRepository.findById(employeeRequest.getEmployeeId()).orElse(null);
        if (employee == null) return false; // Nếu không có nhân viên thì trả về false

        // Cập nhật thông tin của nhân viên
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setEmployeeBirthDate(employeeRequest.getEmployeeBirthDate());
        employee.setEmployeeEmail(employeeRequest.getEmployeeEmail());
        employee.setEmployeeTelephone(employeeRequest.getEmployeeTelephone());
        employee.setEmployeeNameKana(employeeRequest.getEmployeeNameKana());
        employee.setEmployeeLoginId(employeeRequest.getEmployeeLoginId());

        // Mã hóa mật khẩu nếu có
        if (employeeRequest.getEmployeeLoginPassword() != null && !employeeRequest.getEmployeeLoginPassword().isEmpty()) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // Khởi tạo encoder
            String encodedPassword = passwordEncoder.encode(employeeRequest.getEmployeeLoginPassword()); // Mã hóa mật khẩu
            employee.setEmployeeLoginPassword(encodedPassword); // Cập nhật mật khẩu đã mã hóa
        }

        // Cập nhật phòng ban
        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElse(new Department()); // Nếu không tìm thấy thì tạo mới phòng ban
        employee.setDepartment(department); // Thiết lập phòng ban cho nhân viên

        // Xóa các chứng chỉ cũ của nhân viên
        employeeCertificationRepository.deleteByEmployeeId(employeeRequest.getEmployeeId()); // Xóa chứng chỉ cũ

        // Thêm các chứng chỉ mới nếu có
        if (employeeRequest.getCertifications() != null && !employeeRequest.getCertifications().isEmpty()) {
            for (CertificationRequest certificationRequest : employeeRequest.getCertifications()) {
                Certification certification = certificationRepository.findById(certificationRequest.getCertificationId())
                        .orElse(null); // Tìm kiếm chứng chỉ
                if (certification != null) { // Nếu chứng chỉ tồn tại
                    EmployeesCertification employeesCertification = new EmployeesCertification(); // Tạo mới chứng chỉ cho nhân viên
                    employeesCertification.setEmployee(employee); // Thiết lập nhân viên
                    employeesCertification.setCertification(certification); // Thiết lập chứng chỉ
                    employeesCertification.setStartDate(certificationRequest.getCertificationStartDate()); // Thiết lập ngày bắt đầu
                    employeesCertification.setEndDate(certificationRequest.getCertificationEndDate()); // Thiết lập ngày kết thúc
                    employeesCertification.setScore(certificationRequest.getCertificationScore()); // Thiết lập điểm
                    employee.getEmployeesCertifications().add(employeesCertification); // Thêm chứng chỉ vào danh sách chứng chỉ của nhân viên
                }
            }
        }

        employeeRepository.save(employee);
        return true;
    }

}
