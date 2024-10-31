/**
 * Copyright(C) 2024  Luvina
 * CertificationRequest.java, 12/10/2024 KhanhNV
 */
package com.luvina.la.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Lớp CertificationRequest được sử dụng để nhận dữ liệu yêu cầu cho chứng chỉ.
 * Chứa thông tin về ID chứng chỉ, ngày bắt đầu, ngày kết thúc và điểm số của chứng chỉ.
 */
@Data
public class CertificationRequest {
    private Long certificationId; // ID của chứng chỉ

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date certificationStartDate; // Ngày bắt đầu của chứng chỉ, định dạng "yyyy/MM/dd"

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date certificationEndDate; // Ngày kết thúc của chứng chỉ, định dạng "yyyy/MM/dd"

    private Double certificationScore; // Điểm số của chứng chỉ
}
