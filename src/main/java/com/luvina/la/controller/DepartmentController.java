/**
 * Copyright(C) 2024  Luvina
 * DepartmentController.java, 05/10/2024 KhanhNV
 */
package com.luvina.la.controller;

import com.luvina.la.dto.DepartmentDTO;
import com.luvina.la.payload.ApiResponse;
import com.luvina.la.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.luvina.la.config.constants.MessageConstants.*;

/**
 * Controller xử lý các yêu cầu HTTP liên quan đến department
 * Bao gồm chức năng lấy danh sách tất cả các department.
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * Lấy danh sách tất cả các department từ DepartmentService
     *
     * @return
     *  Trường hợp thành công
     *  {
     *     "code": "200",
     *     "departments": [Danh sách các department]
     *  }
     *  Trường hợp lỗi
     *  {
     *     "code": "500",
     *     "message": {
     *        "code": "ER023",
     *        "params": []
     *     }
     *  }
     */
    @GetMapping
    public ResponseEntity<?> getAllDepartments() {
        try {
            // Gọi service để lấy danh sách các department
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            Map<String, Object> response = new HashMap<>();
            response.put("code", CODE_200);
            response.put("departments", departments);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Xử lý lỗi và trả về thông báo lỗi
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
