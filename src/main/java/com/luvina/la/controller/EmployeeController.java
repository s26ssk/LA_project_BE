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

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private EmployeeValidator employeeValidator;

    @GetMapping
    public ResponseEntity<?> getEmployees(@RequestParam(name = "employee_name", required = false) String employeeName,
                                          @RequestParam(name = "department_id", required = false) Long departmentId,
                                          @RequestParam(name = "ord_employee_name", required = false) String ordEmployeeName,
                                          @RequestParam(name = "ord_certification_name", required = false) String ordCertificationName,
                                          @RequestParam(name = "ord_end_date", required = false) String ordEndDate,
                                          @RequestParam(defaultValue = "0") int offset,
                                          @RequestParam(defaultValue = "5") int limit) {

        try {
            if (employeeName != null) {
                employeeName = employeeName.trim();
            }

            // Validate order parameters
            List<String> validOrders = Arrays.asList("asc", "desc");
            if ((ordEmployeeName != null && !validOrders.contains(ordEmployeeName)) ||
                    (ordCertificationName != null && !validOrders.contains(ordCertificationName)) ||
                    (ordEndDate != null && !validOrders.contains(ordEndDate))) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER021, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Validate offset
            if (offset < 0) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER018, getParams(OFFSET)), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            // Validate limit
            if (limit <= 0) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER018, getParams(LIMIT)), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            int totalRecords = employeeService.countTotalRecords(employeeName, departmentId);

            List<EmployeeDTO> employeeDTO = employeeService.getEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, offset, limit);
            EmployeeResponseDTO response = new EmployeeResponseDTO();
            response.setCode(CODE_200);
            response.setTotalRecords(totalRecords);
            response.setEmployees(employeeDTO);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest employeeRequest) {
        try {
            ApiResponse validationResponse = employeeValidator.validateAddEmployee(employeeRequest);
            if (validationResponse != null) {
                return new ResponseEntity<>(validationResponse, HttpStatus.OK);
            }

            Long employeeId = employeeService.addEmployee(employeeRequest);
            ApiResponse messageResponse = new ApiResponse(MSG001, "", Collections.emptyList());

            Map<String, Object> response = new HashMap<>();
            response.put("code", CODE_200);
            response.put("employeeId", employeeId);
            response.put("message", messageResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeDetail(@PathVariable Long id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER001, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }
            boolean employeeExists = employeeService.existsById(id);
            if (!employeeExists) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER014, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }
            EmployeeDetailDTO employeeDetail = employeeService.getEmployeeDetail(id);
            if (employeeDetail != null) {
                employeeDetail.setCode(CODE_200);
                return new ResponseEntity<>(employeeDetail, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER013, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER001, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }
            boolean employeeExists = employeeService.existsById(id);
            if (!employeeExists) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER014, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }
            boolean isDeleted = employeeService.deleteEmployeeById(id);

            if (isDeleted) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", CODE_200);
                response.put("employeeId", id);
                response.put("message", new ApiResponse(MSG003, "", Collections.emptyList()));
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER015, getParams(ID_PARAMETER)), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) {
        try {
            if (id == null) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER001, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }
            boolean employeeExists = employeeService.existsById(id);
            if (!employeeExists) {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER013, getParams(ID_PARAMETER)), HttpStatus.BAD_REQUEST);
            }
            employeeRequest.setEmployeeId(id);
            ApiResponse validationResponse = employeeValidator.validateEditEmployee(employeeRequest);
            if (validationResponse != null) {
                return new ResponseEntity<>(validationResponse, HttpStatus.OK);
            }

            boolean isUpdated = employeeService.updateEmployee(id, employeeRequest);

            if (isUpdated) {
                ApiResponse messageResponse = new ApiResponse(MSG002, "", Collections.emptyList());
                Map<String, Object> response = new HashMap<>();
                response.put("code", CODE_200);
                response.put("employeeId", id);
                response.put("message", messageResponse);
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>(new ApiResponse(CODE_500, ER015, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
