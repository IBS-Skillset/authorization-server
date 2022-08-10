package com.mystays.authorizationserver.handler;

import com.mystays.authorizationserver.model.ErrorResponse;
import com.mystays.authorizationserver.util.ResponseEntityBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException badCredentialsException){
        log.error("Bad Credentials" ,badCredentialsException);
        return ResponseEntityBuilder.build(HttpStatus.UNAUTHORIZED, badCredentialsException.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UsernameNotFoundException usernameNotFoundException){
        log.error("Username Not Found" ,usernameNotFoundException);
        return ResponseEntityBuilder.build(HttpStatus.UNAUTHORIZED, usernameNotFoundException.getMessage());
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBearerToken(InvalidBearerTokenException invalidBearerTokenException){
        log.error("Invalid Bearer Token" ,invalidBearerTokenException);
        return ResponseEntityBuilder.build(HttpStatus.UNAUTHORIZED, invalidBearerTokenException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("Invalid request parameters" ,methodArgumentNotValidException);
        List<String> errorMessage = new ArrayList<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors()
                .forEach(error -> { String fieldName = ((FieldError) error).getField();
                                    String message = error.getDefaultMessage();
                                    errorMessage.add(fieldName + " : " + message); });
        return ResponseEntityBuilder.build(HttpStatus.BAD_REQUEST, String.join(", ", errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception){
        log.error("Internal Server Error" ,exception);
        return ResponseEntityBuilder.build(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }


}
