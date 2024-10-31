/**
 * Copyright(C) 2024  Luvina
 * EmployeeController.java, 04/10/2024 KhanhNV
 */
package com.luvina.la.controller;

import com.luvina.la.dto.EmployeeDTO;
import com.luvina.la.dto.EmployeeDetailDTO;
import com.luvina.la.payload.*;
import com.luvina.la.service.IEmployeeService;
import com.luvina.la.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.luvina.la.config.constants.MessageConstants.*;

/**
 * Controller xử lý các yêu cầu HTTP liên quan đến employee
 * Bao gồm các chức năng lấy danh sách, thêm mới, lấy thông tin chi tiết và xóa employee.
 */
@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private EmployeeValidator employeeValidator;

        /**
     * Lấy danh sách employee từ EmployeeService
     *
     * @param employeeName Tên nhân viên
     * @param departmentId Mã phòng ban
     * @param ordEmployeeName ASC hoặc DESC
     * @param ordCertificationName ASC hoặc DESC
     * @param ordEndDate ASC hoặc DESC
     * @param offset Số nguyên dương, vị trí bắt đầu lấy dữ liệu
     * @param limit Số lượng bản ghi muốn lấy
     * @return
     *  Trường hợp thành công
     *  {
     *     "code": "200",
     *     "totalRecords": tổng số bản ghi,
     *     "employees": [Danh sách các employee]
     *  }
     *  Trường hợp lỗi
     *  {
     *     "code": "500",
     *     "message": { lỗi tương ứng }
     *  }
     */
        @GetMapping
        public ResponseEntity<?> getEmployees(@RequestParam(name = "employee_name", required = false) String employeeName,
                                              @RequestParam(name = "department_id", required = false) Long departmentId,
                                              @RequestParam(name = "ord_employee_name", required = false) String ordEmployeeName,
                                              @RequestParam(name = "ord_certification_name", required = false) String ordCertificationName,
                                              @RequestParam(name = "ord_end_date", required = false) String ordEndDate,
                                              @RequestParam(defaultValue = "0") int offset,
                                              @RequestParam(defaultValue = "5") int limit) {

            try {
                // Loại bỏ khoảng trắng thừa trong tên nhân viên nếu có
                if (employeeName != null) {
                    employeeName = employeeName.trim();
                    employeeName = employeeName.replace("%", "\\%").replace("_", "\\_");
                }

                // Kiểm tra giá trị các tham số order
                List<String> validOrders = Arrays.asList("asc", "desc");
                if ((ordEmployeeName != null && !validOrders.contains(ordEmployeeName)) ||
                        (ordCertificationName != null && !validOrders.contains(ordCertificationName)) ||
                        (ordEndDate != null && !validOrders.contains(ordEndDate))) {
                    // Nếu giá trị thứ tự không hợp lệ, trả về phản hồi lỗi
                    return new ResponseEntity<>(new ApiResponse(CODE_500, ER021, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // Kiểm tra offset
                if (offset < 0) {
                    return new ResponseEntity<>(new ApiResponse(CODE_500, ER018, getParams(OFFSET)), HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // Kiểm tra limit
                if (limit <= 0) {
                    return new ResponseEntity<>(new ApiResponse(CODE_500, ER018, getParams(LIMIT)), HttpStatus.INTERNAL_SERVER_ERROR);
                }

                // Đếm tổng số bản ghi thỏa mãn điều kiện
                int totalRecords = employeeService.countTotalRecords(employeeName, departmentId);

                // Lấy danh sách nhân viên từ service
                List<EmployeeDTO> employeeDTO = employeeService.getEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);

                // Tạo đối tượng phản hồi và thiết lập các thông tin
                EmployeeResponseDTO response = new EmployeeResponseDTO();
                response.setCode(CODE_200);
                response.setTotalRecords(totalRecords);
                response.setEmployees(employeeDTO);

                // Trả về danh sách nhân viên với mã trạng thái 200
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception e) {
                // Xử lý ngoại lệ và trả về lỗi hệ thống
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    /**
     * Thêm mới employee
     * @param employeeRequest Thông tin employee từ FE
     * @return
     *  Trường hợp thành công
     *  {
     *     "code": "200",
     *     "employeeId": Mã nhân viên vừa được thêm,
     *     "message": { "code": "MSG001", "params": [] }
     *  }
     *  Trường hợp lỗi
     *  {
     *     "code": "500",
     *     "message": { lỗi tương ứng }
     *  }
     */
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            // Kiểm tra validate các trường của employeeRequest
            ApiResponse validationResponse = employeeValidator.validateEmployee(employeeRequest, false);
            if (validationResponse != null) {
                // Nếu yêu cầu không hợp lệ, trả về thông tin lỗi
                return new ResponseEntity<>(validationResponse, HttpStatus.OK);
            }

            // Gọi service để thêm nhân viên và nhận ID của nhân viên mới thêm
            Long employeeId = employeeService.addEmployee(employeeRequest);

            // Tạo thông điệp phản hồi thành công
            ApiResponse messageResponse = new ApiResponse(MSG001, "", Collections.emptyList());

            // Tạo phản hồi chứa mã thành công, ID của nhân viên mới thêm và thông điệp
            Map<String, Object> response = new HashMap<>();
            response.put("code", CODE_200);
            response.put("employeeId", employeeId);
            response.put("message", messageResponse);

            // Trả về phản hồi thành công
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi hệ thống
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Lấy thông tin chi tiết của employee
     * @param id Mã nhân viên
     * @return
     *  Trường hợp thành công: Thông tin chi tiết của employee
     *  Trường hợp lỗi
     *  {
     *     "code": "500",
     *     "message": { lỗi tương ứng }
     *  }
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeDetail(@PathVariable Long id) {
        try {
            // Kiểm tra xem ID có hợp lệ không
            if (id == null) {
                // Nếu ID không hợp lệ, trả về phản hồi lỗi
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER001, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra xem nhân viên có tồn tại trong hệ thống không
            boolean employeeExists = employeeService.existsById(id);
            if (!employeeExists) {
                // Nếu nhân viên không tồn tại, trả về phản hồi lỗi
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER013, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }

            // Lấy thông tin chi tiết của nhân viên từ employeeService
            EmployeeDetailDTO employeeDetail = employeeService.getEmployeeDetail(id);
            if (employeeDetail != null) {
                // Nếu thông tin chi tiết tồn tại, thêm mã thành công và trả về phản hồi
                employeeDetail.setCode(CODE_200);
                return new ResponseEntity<>(employeeDetail, HttpStatus.OK);
            } else {
                // Nếu không tìm thấy thông tin chi tiết, trả về phản hồi lỗi
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER013, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi hệ thống
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Xóa employee
     * @param id Mã nhân viên cần xóa
     * @return
     *  Trường hợp thành công
     *  {
     *     "code": "200",
     *     "employeeId": Mã nhân viên vừa được xóa,
     *     "message": { "code": "MSG003", "params": [] }
     *  }
     *  Trường hợp lỗi
     *  {
     *     "code": "500",
     *     "message": { lỗi tương ứng }
     *  }
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            // Kiểm tra xem ID có hợp lệ không
            if (id == null) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER001, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }

            // Kiểm tra xem nhân viên có tồn tại trong hệ thống không
            boolean employeeExists = employeeService.existsById(id);
            if (!employeeExists) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER014, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }

            // Gọi service để thực hiện xóa nhân viên
            boolean isDeleted = employeeService.deleteEmployeeById(id);

            if (isDeleted) {
                // Tạo phản hồi thành công nếu xóa thành công
                Map<String, Object> response = new HashMap<>();
                response.put("code", CODE_200);
                response.put("employeeId", id);
                response.put("message", new ApiResponse(MSG003, "", Collections.emptyList()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // Nếu xóa không thành công, trả về lỗi
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER015, getParams(ID_PARAMETER)), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi hệ thống
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Cập nhật thông tin của nhân viên dựa trên ID được cung cấp.
     *
     * @param employeeRequest Đối tượng chứa dữ liệu cập nhật cho nhân viên.
     * @return ResponseEntity chứa thông tin phản hồi về kết quả cập nhật.
     */

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            // Kiểm tra các lỗi hợp lệ của dữ liệu cập nhật
            ApiResponse validationResponse = employeeValidator.validateEmployee(employeeRequest, true);
            if (validationResponse != null) {
                return new ResponseEntity<>(validationResponse, HttpStatus.OK);
            }

            // Gọi service để thực hiện cập nhật nhân viên
            boolean isUpdated = employeeService.updateEmployee(employeeRequest);

            if (isUpdated) {
                // Tạo phản hồi thành công nếu cập nhật thành công
                ApiResponse messageResponse = new ApiResponse(MSG002, "", Collections.emptyList());
                Map<String, Object> response = new HashMap<>();
                response.put("code", CODE_200);
                response.put("employeeId", employeeRequest.getEmployeeId());
                response.put("message", messageResponse);
                return ResponseEntity.ok(response);
            } else {
                // Nếu cập nhật không thành công, trả về lỗi
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER015, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về lỗi hệ thống
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
