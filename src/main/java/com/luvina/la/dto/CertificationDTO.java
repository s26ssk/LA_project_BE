package com.luvina.la.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CertificationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long certificationId;
    private String certificationName;
}
