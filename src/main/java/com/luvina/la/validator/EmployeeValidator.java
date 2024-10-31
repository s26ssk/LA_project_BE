/**
 * Copyright(C) 2024  Luvina
 * EmployeeValidator.java, 14/10/2024 KhanhNV
 */
package com.luvina.la.validator;

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

/**
 * EmployeeValidator là lớp thực hiện các phương thức kiểm tra tính hợp lệ cho thông tin nhân viên.
 */
@Component
public class EmployeeValidator {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CertificationRepository certificationRepository;

    public static final String DATE_FORMAT = "yyyy/MM/dd";

    /**
     * Kiểm tra tính hợp lệ cho thông tin nhân viên khi thêm mới hoặc chỉnh sửa.
     * @param request Đối tượng chứa thông tin nhân viên.
     * @param isEdit Biến boolean xác định liệu đang chỉnh sửa hay thêm mới.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    public ApiResponse validateEmployee(EmployeeRequest request, boolean isEdit) {

        ApiResponse response = isEdit
                ? validateEditEmployeeLoginId(request.getEmployeeLoginId(), request.getEmployeeId())
                : validateAddEmployeeLoginId(request.getEmployeeLoginId());

        if (response == null) response = validateEmployeeName(request.getEmployeeName());
        if (response == null) response = validateEmployeeNameKana(request.getEmployeeNameKana());
        if (response == null) response = validateEmployeeBirthDate(request.getEmployeeBirthDate());
        if (response == null) response = validateEmployeeEmail(request.getEmployeeEmail());
        if (response == null) response = validateEmployeeTelephone(request.getEmployeeTelephone());

        if (isEdit) {
            if (response == null) response = validateEditEmployeeLoginPassword(request.getEmployeeLoginPassword());
        } else {
            if (response == null) response = validateAddEmployeeLoginPassword(request.getEmployeeLoginPassword());
        }

        if (isEdit){
            if (response == null) response = validatEmployeeId(request.getEmployeeId());
        }

        if (response == null) response = validateDepartmentId(request.getDepartmentId());
        if (response == null) response = validateCertifications(request.getCertifications());

        return response;
    }

    private ApiResponse validatEmployeeId(Long employeeId) {
        ApiResponse response = null;

        // Kiểm tra nếu employeeId không tồn tại
        if (employeeId == null) {
            response = new ApiResponse(CODE_500, ER001, getParams(ID_PARAMETER));
        }
        // Kiểm tra nếu employeeId không tồn tại trong bảng employees
        else if (!employeeRepository.existsById(employeeId)) {
            response = new ApiResponse(CODE_500, ER013, getParams(ID_PARAMETER));
        }

        return response;
    }


    /**
     * Kiểm tra tính hợp lệ cho employeeLoginId khi thêm nhân viên.
     * @param employeeLoginId Địa chỉ đăng nhập của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateAddEmployeeLoginId(String employeeLoginId) {
        ApiResponse response = null;

        if (employeeLoginId == null || employeeLoginId.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(ACCOUNT_NAME));
        } else if (employeeLoginId.length() > 50) {
            response = new ApiResponse(CODE_500, ER006, getParams(ACCOUNT_NAME));
        } else if (!Pattern.matches("^[a-zA-Z0-9_]+$", employeeLoginId) || Character.isDigit(employeeLoginId.charAt(0))) {
            response = new ApiResponse(CODE_500, ER019, getParams(ACCOUNT_NAME));
        } else if (employeeRepository.existsByEmployeeLoginId(employeeLoginId)) {
            response = new ApiResponse(CODE_500, ER003, getParams(ACCOUNT_NAME));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho employeeLoginId khi chỉnh sửa nhân viên.
     * @param employeeLoginId Địa chỉ đăng nhập của nhân viên.
     * @param employeeId ID của nhân viên hiện tại.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEditEmployeeLoginId(String employeeLoginId, Long employeeId) {
        ApiResponse response = null;

        if (employeeLoginId == null || employeeLoginId.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(ACCOUNT_NAME));
        } else if (employeeLoginId.length() > 50) {
            response = new ApiResponse(CODE_500, ER006, getParams(ACCOUNT_NAME));
        } else if (!Pattern.matches("^[a-zA-Z0-9_]+$", employeeLoginId) || Character.isDigit(employeeLoginId.charAt(0))) {
            response = new ApiResponse(CODE_500, ER019, getParams(ACCOUNT_NAME));
        } else if (employeeRepository.existsByEmployeeLoginId(employeeLoginId) &&
                !employeeRepository.findById(employeeId).get().getEmployeeLoginId().equals(employeeLoginId)) {
            response = new ApiResponse(CODE_500, ER003, getParams(ACCOUNT_NAME));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho tên nhân viên.
     * @param employeeName Tên của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEmployeeName(String employeeName) {
        ApiResponse response = null;

        if (employeeName == null || employeeName.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(FULL_NAME));
        } else if (employeeName.length() > 125) {
            response = new ApiResponse(CODE_500, ER006, getParams(FULL_NAME));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho tên Kana của nhân viên.
     * @param employeeNameKana Tên Kana của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEmployeeNameKana(String employeeNameKana) {
        ApiResponse response = null;

        if (employeeNameKana == null || employeeNameKana.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(KANA_NAME));
        } else if (employeeNameKana.length() > 125) {
            response = new ApiResponse(CODE_500, ER006, getParams(KANA_NAME));
        } else if (!Pattern.matches("^[\\uFF61-\\uFF9F]+$", employeeNameKana)) {
            response = new ApiResponse(CODE_500, ER009, getParams(KANA_NAME));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho ngày sinh của nhân viên.
     * @param employeeBirthDate Ngày sinh của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEmployeeBirthDate(Date employeeBirthDate) {
        ApiResponse response = null;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);

        if (employeeBirthDate == null) {
            response = new ApiResponse(CODE_500, ER001, getParams(BIRTH_DATE));
        } else {
            try {
                String formattedDate = sdf.format(employeeBirthDate);
                if (!formattedDate.equals(sdf.format(sdf.parse(formattedDate)))) {
                    response = new ApiResponse(CODE_500, ER011, getParams(BIRTH_DATE));
                } else if (!sdf.format(employeeBirthDate).equals(sdf.format(employeeBirthDate))) {
                    response = new ApiResponse(CODE_500, ER005, getParams(BIRTH_DATE, DATE_FORMAT_MESSAGE));
                }
            } catch (ParseException e) {
                response = new ApiResponse(CODE_500, ER011, getParams(BIRTH_DATE));
            }
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho email của nhân viên.
     * @param employeeEmail Địa chỉ email của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEmployeeEmail(String employeeEmail) {
        ApiResponse response = null;

        if (employeeEmail == null || employeeEmail.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(EMAIL));
        } else if (employeeEmail.length() > 125) {
            response = new ApiResponse(CODE_500, ER006, getParams(EMAIL));
        } else if (!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", employeeEmail)) {
            response = new ApiResponse(CODE_500, ER010, getParams(EMAIL));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho số điện thoại của nhân viên.
     * @param employeeTelephone Số điện thoại của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEmployeeTelephone(String employeeTelephone) {
        ApiResponse response = null;

        if (employeeTelephone == null || employeeTelephone.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(PHONE));
        } else if (employeeTelephone.length() > 50) {
            response = new ApiResponse(CODE_500, ER006, getParams(PHONE));
        } else if (!Pattern.matches("^[0-9]+$", employeeTelephone)) {
            response = new ApiResponse(CODE_500, ER008, getParams(PHONE));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho mật khẩu của nhân viên khi thêm mới.
     * @param employeeLoginPassword Mật khẩu của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateAddEmployeeLoginPassword(String employeeLoginPassword) {
        ApiResponse response = null;

        if (employeeLoginPassword == null || employeeLoginPassword.isEmpty()) {
            response = new ApiResponse(CODE_500, ER001, getParams(PASSWORD));
        } else if (employeeLoginPassword.length() < 8 || employeeLoginPassword.length() > 50) {
            response = new ApiResponse(CODE_500, ER007, getParams(PASSWORD));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho mật khẩu của nhân viên khi chỉnh sửa.
     * @param employeeLoginPassword Mật khẩu của nhân viên.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateEditEmployeeLoginPassword(String employeeLoginPassword) {
        ApiResponse response = null;

        if (!employeeLoginPassword.isEmpty()) {
            if (employeeLoginPassword.length() < 8 || employeeLoginPassword.length() > 50) {
                response = new ApiResponse(CODE_500, ER007, getParams(PASSWORD));
            }
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho ID phòng ban.
     * @param departmentId ID của phòng ban.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateDepartmentId(Long departmentId) {
        ApiResponse response = null;

        if (departmentId == null) {
            response = new ApiResponse(CODE_500, ER002, getParams(GROUP));
        } else if (departmentId <= 0) {
            response = new ApiResponse(CODE_500, ER018, getParams(GROUP));
        } else if (!departmentRepository.existsById(departmentId)) {
            response = new ApiResponse(CODE_500, ER004, getParams(GROUP));
        }

        return response;
    }

    /**
     * Kiểm tra tính hợp lệ cho danh sách chứng chỉ của nhân viên.
     * @param certifications Danh sách chứng chỉ.
     * @return ApiResponse chứa thông tin về lỗi nếu có, hoặc null nếu không có lỗi.
     */
    private ApiResponse validateCertifications(Set<CertificationRequest> certifications) {
        if (certifications == null || certifications.isEmpty()) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        ApiResponse response = null;

        for (CertificationRequest certRequest : certifications) {
            if (certRequest.getCertificationId() == null) {
                return null;
            }
            if (certRequest.getCertificationId() <= 0) {
                response = new ApiResponse(CODE_500, ER018, getParams(CERTIFICATION));
                break;
            }
            if (!certificationRepository.existsById(certRequest.getCertificationId())) {
                response = new ApiResponse(CODE_500, ER004, getParams(CERTIFICATION));
                break;
            }

            if (certRequest.getCertificationStartDate() == null) {
                response = new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_START_DATE));
                break;
            }
            if (!isValidDate(certRequest.getCertificationStartDate())) {
                response = new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_START_DATE));
                break;
            }
            if (!isDateFormattedCorrectly(certRequest.getCertificationStartDate(), dateFormat)) {
                response = new ApiResponse(CODE_500, ER005, getParams(CERTIFICATION_START_DATE, DATE_FORMAT_MESSAGE));
                break;
            }

            if (certRequest.getCertificationEndDate() == null) {
                response = new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_END_DATE));
                break;
            }
            if (!isValidDate(certRequest.getCertificationEndDate())) {
                response = new ApiResponse(CODE_500, ER001, getParams(CERTIFICATION_END_DATE));
                break;
            }
            if (!isDateFormattedCorrectly(certRequest.getCertificationEndDate(), dateFormat)) {
                response = new ApiResponse(CODE_500, ER005, getParams(CERTIFICATION_END_DATE, DATE_FORMAT_MESSAGE));
                break;
            }
            if (certRequest.getCertificationStartDate().after(certRequest.getCertificationEndDate())) {
                response = new ApiResponse(CODE_500, ER012, getParams(CERTIFICATION_START_DATE, CERTIFICATION_END_DATE));
                break;
            }

            if (certRequest.getCertificationScore() == null) {
                response = new ApiResponse(CODE_500, ER001, getParams(SCORE));
                break;
            }
            if (certRequest.getCertificationScore() < 0) {
                response = new ApiResponse(CODE_500, ER018, getParams(SCORE));
                break;
            }
        }
        return response;
    }


    /**
     * Kiểm tra xem ngày có hợp lệ hay không.
     * @param date Ngày cần kiểm tra.
     * @return true nếu ngày hợp lệ, false nếu không.
     */
    private boolean isValidDate(Date date) {
        try {
            return date != null && date instanceof Date; // Kiểm tra xem ngày không null và là một thể hiện của lớp Date
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Kiểm tra xem ngày có định dạng đúng theo định dạng đã cho hay không.
     * @param date Ngày cần kiểm tra.
     * @param dateFormat Định dạng ngày cần kiểm tra.
     * @return true nếu ngày có định dạng đúng, false nếu không.
     */
    private boolean isDateFormattedCorrectly(Date date, SimpleDateFormat dateFormat) {
        return dateFormat.format(date).equals(dateFormat.format(date)); // So sánh định dạng ngày với định dạng đã cho
    }
}
