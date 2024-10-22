package com.luvina.la.payload;

import lombok.Data;

import java.util.List;
@Data
public class ApiResponse {

    private String code;
    private String message;
    private List<String> params;

    public ApiResponse(String code, String message, List<String> params) {
        this.code = code;
        this.message = message;
        this.params = params;
    }
}
