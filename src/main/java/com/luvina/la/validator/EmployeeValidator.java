package com.luvina.la.validator;

import com.luvina.la.entity.Employee;
import com.luvina.la.payload.ApiResponse;
import com.luvina.la.payload.CertificationRequest;
import com.luvina.la.payload.EmployeeRequest;
import com.luvina.la.repository.CertificationRepository;
import com.luvina.la.repository.DepartmentRepository;
import com.luvina.la.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import static com.luvina.la.config.constants.MessageConstants.*;

@Component
public class EmployeeValidator {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CertificationRepository certificationRepository;

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    public ApiResponse validateAddEmployee(EmployeeRequest request) {
        ApiResponse response;

        response = validateAddEmployeeLoginId(request.getEmployeeLoginId());
        if (response != null) return response;

        response = validateEmployeeName(request.getEmployeeName());
        if (response != null) return response;

        response = validateEmployeeNameKana(request.getEmployeeNameKana());
        if (response != null) return response;

        response = validateEmployeeBirthDate(request.getEmployeeBirthDate());
        if (response != null) return response;

        response = validateEmployeeEmail(request.getEmployeeEmail());
        if (response != null) return response;

        response = validateEmployeeTelephone(request.getEmployeeTelephone());
        if (response != null) return response;

        response = validateAddEmployeeLoginPassword(request.getEmployeeLoginPassword());
        if (response != null) return response;

        response = validateDepartmentId(request.getDepartmentId());
        if (response != null) return response;

        response = validateCertifications(request.getCertifications());
        return response;
    }
    public ApiResponse validateEditEmployee(EmployeeRequest request) {
        ApiResponse response;

        response = validateEditEmployeeLoginId(request.getEmployeeLoginId(), request.getEmployeeId());
        if (response != null) return response;

        response = validateEmployeeName(request.getEmployeeName());
        if (response != null) return response;

        response = validateEmployeeNameKana(request.getEmployeeNameKana());
        if (response != null) return response;

        response = validateEmployeeBirthDate(request.getEmployeeBirthDate());
        if (response != null) return response;

        response = validateEmployeeEmail(request.getEmployeeEmail());
        if (response != null) return response;

        response = validateEmployeeTelephone(request.getEmployeeTelephone());
        if (response != null) return response;

        response = validateEditEmployeeLoginPassword(request.getEmployeeLoginPassword());
        if (response != null) return response;

        response = validateDepartmentId(request.getDepartmentId());
        if (response != null) return response;

        response = validateCertifications(request.getCertifications());
        return response;
    }

    private ApiResponse validateAddEmployeeLoginId(String employeeLoginId) {
        if (employeeLoginId == null || employeeLoginId.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(ACCOUNT_NAME));
        }
        if (employeeLoginId.length() > 50) {
            return new ApiResponse(CODE_500, ER006, getParams(ACCOUNT_NAME));
        }
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", employeeLoginId) || Character.isDigit(employeeLoginId.charAt(0))) {
            return new ApiResponse(CODE_500, ER019, getParams(ACCOUNT_NAME));
        }
        if (employeeRepository.existsByEmployeeLoginId(employeeLoginId)) {
            return new ApiResponse(CODE_500, ER003, getParams(ACCOUNT_NAME));
        }
        return null;
    }
    private ApiResponse validateEditEmployeeLoginId(String employeeLoginId, Long employeeId) {
        if (employeeLoginId == null || employeeLoginId.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(ACCOUNT_NAME));
        }
        if (employeeLoginId.length() > 50) {
            return new ApiResponse(CODE_500, ER006, getParams(ACCOUNT_NAME));
        }
        if (!Pattern.matches("^[a-zA-Z0-9_]+$", employeeLoginId) || Character.isDigit(employeeLoginId.charAt(0))) {
            return new ApiResponse(CODE_500, ER019, getParams(ACCOUNT_NAME));
        }
        if (employeeRepository.existsByEmployeeLoginIdAndEmployeeIdNot(employeeLoginId, employeeId)) {
            return new ApiResponse(CODE_500, ER003, getParams(ACCOUNT_NAME));
        }
        return null;
    }

    private ApiResponse validateEmployeeName(String employeeName) {
        if (employeeName == null || employeeName.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(FULL_NAME));
        }
        if (employeeName.length() > 125) {
            return new ApiResponse(CODE_500, ER006, getParams(FULL_NAME));
        }
        return null;
    }

    private ApiResponse validateEmployeeNameKana(String employeeNameKana) {
        if (employeeNameKana == null || employeeNameKana.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(KANA_NAME));
        }
        if (employeeNameKana.length() > 125) {
            return new ApiResponse(CODE_500, ER006, getParams(KANA_NAME));
        }
        if (!Pattern.matches("^[\\uFF61-\\uFF9F]+$", employeeNameKana)) {
            return new ApiResponse(CODE_500, ER009, getParams(KANA_NAME));
        }
        return null;
    }

    private ApiResponse validateEmployeeBirthDate(Date employeeBirthDate) {
        if (employeeBirthDate == null) {
            return new ApiResponse(CODE_500, ER001, getParams(BIRTH_DATE));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        try {
            String formattedDate = sdf.format(employeeBirthDate);
            if (!formattedDate.equals(sdf.format(sdf.parse(formattedDate)))) {
                return new ApiResponse(CODE_500, ER011, getParams(BIRTH_DATE));
            }
        } catch (ParseException e) {
            return new ApiResponse(CODE_500, ER011, getParams(BIRTH_DATE));
        }

        if (!sdf.format(employeeBirthDate).equals(sdf.format(employeeBirthDate))) {
            return new ApiResponse(CODE_500, ER005, getParams(BIRTH_DATE, DATE_FORMAT_MESSAGE));
        }
        return null;
    }

    private ApiResponse validateEmployeeEmail(String employeeEmail) {
        if (employeeEmail == null || employeeEmail.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(EMAIL));
        }
        if (employeeEmail.length() > 125) {
            return new ApiResponse(CODE_500, ER006, getParams(EMAIL));
        }
        if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", employeeEmail)) {
            return new ApiResponse(CODE_500, ER010, getParams(EMAIL));
        }
        return null;
    }

    private ApiResponse validateEmployeeTelephone(String employeeTelephone) {
        if (employeeTelephone == null || employeeTelephone.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(PHONE));
        }
        if (employeeTelephone.length() > 50) {
            return new ApiResponse(CODE_500, ER006, getParams(PHONE));
        }
        if (!Pattern.matches("^[0-9]+$", employeeTelephone)) {
            return new ApiResponse(CODE_500, ER008, getParams(PHONE));
        }
        return null;
    }

    private ApiResponse validateAddEmployeeLoginPassword(String employeeLoginPassword) {
        if (employeeLoginPassword == null || employeeLoginPassword.isEmpty()) {
            return new ApiResponse(CODE_500, ER001, getParams(PASSWORD));
        }
        if (employeeLoginPassword.length() < 8 || employeeLoginPassword.length() > 50) {
            return new ApiResponse(CODE_500, ER007, getParams(PASSWORD));
        }
        return null;
    }
    private ApiResponse validateEditEmployeeLoginPassword(String employeeLoginPassword) {
        if (!employeeLoginPassword.isEmpty()) {
            if (employeeLoginPassword.length() < 8 || employeeLoginPassword.length() > 50) {
                return new ApiResponse(CODE_500, ER007, getParams(PASSWORD));
            }
        }
        return null;
    }


    private ApiResponse validateDepartmentId(Long departmentId) {
        if (departmentId == null) {
            return new ApiResponse(CODE_500, ER002, getParams(GROUP));
        }
        if (departmentId <= 0) {
            return new ApiResponse(CODE_500, ER018, getParams(GROUP));
        }
        if (!departmentRepository.existsById(departmentId)) {
            return new ApiResponse(CODE_500, ER004, getParams(GROUP));
        }
        return null;
    }

    private ApiResponse validateCertifications(Set<CertificationRequest> certifications) {
        if (certifications == null || certifications.isEmpty()) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        for (CertificationRequest certRequest : certifications) {
            if (certRequest.getCertificationId() == null) {
                return null;
            }
            if (certRequest.getCertificationId() <= 0) {
                return new ApiResponse(CODE_500, ER018, getParams(CERTIFICATION));
            }
            if (!certificationRepository.existsById(certRequest.getCertificationId())) {
                return new ApiResponse(CODE_500, ER004, getParams(CERTIFICATION));
            }

            if (certRequest.getCertificationStartDate() == null) {
                return new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_START_DATE));
            }
            if (!isValidDate(certRequest.getCertificationStartDate())) {
                return new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_START_DATE));
            }
            if (!isDateFormattedCorrectly(certRequest.getCertificationStartDate(), dateFormat)) {
                return new ApiResponse(CODE_500, ER005, getParams(CERTIFICATION_START_DATE, DATE_FORMAT_MESSAGE));
            }

            if (certRequest.getCertificationEndDate() == null) {
                return new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_END_DATE));
            }
            if (!isValidDate(certRequest.getCertificationEndDate())) {
                return new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_END_DATE));
            }
            if (!isDateFormattedCorrectly(certRequest.getCertificationEndDate(), dateFormat)) {
                return new ApiResponse(CODE_500, ER005, getParams(CERTIFICATION_END_DATE, DATE_FORMAT_MESSAGE));
            }
            if (certRequest.getCertificationStartDate().after(certRequest.getCertificationEndDate())) {
                return new ApiResponse(CODE_500, ER012, getParams(CERTIFICATION_START_DATE, CERTIFICATION_END_DATE));
            }

            if (certRequest.getCertificationScore() == null) {
                return new ApiResponse(CODE_500, ER001, getParams(SCORE));
            }
            if (certRequest.getCertificationScore() < 0) {
                return new ApiResponse(CODE_500, ER018, getParams(SCORE));
            }
        }
        return null;
    }

    private boolean isValidDate(Date date) {
        try {
            return date != null && date instanceof Date;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDateFormattedCorrectly(Date date, SimpleDateFormat dateFormat) {
        return dateFormat.format(date).equals(dateFormat.format(date));
    }
}
