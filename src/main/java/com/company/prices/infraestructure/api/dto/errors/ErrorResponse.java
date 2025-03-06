package com.company.prices.infraestructure.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;
    private List<ErrorDetail> details;

}
