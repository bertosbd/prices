package com.company.prices.infraestructure.api.dto.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetail {

    private String field;
    private String message;
}
