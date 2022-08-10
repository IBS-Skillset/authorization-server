package com.mystays.authorizationserver.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ErrorResponse {

    private String status;
    private int errorCode;
    private String errorText;
}
