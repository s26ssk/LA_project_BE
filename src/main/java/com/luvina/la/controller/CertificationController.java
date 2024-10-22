package com.luvina.la.controller;

import com.luvina.la.dto.CertificationDTO;
import com.luvina.la.payload.ApiResponse;
import com.luvina.la.service.ICertificationService;
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

@RestController
@RequestMapping("/certifications")
public class CertificationController {

    @Autowired
    private ICertificationService certificationService;

    @GetMapping
    public ResponseEntity<?> getAllCertifications() {
        try {
            List<CertificationDTO> certifications = certificationService.getAllCertifications();
            Map<String, Object> response = new HashMap<>();
            response.put("code", CODE_200);
            response.put("certifications", certifications);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(CODE_500, ER023, Collections.emptyList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
