package org.example.innercirclesurvey.common.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.innercirclesurvey.common.exception.dto.ErrorCode;
import org.example.innercirclesurvey.common.exception.dto.ErrorResponse;
import org.example.innercirclesurvey.common.exception.dto.common.CommonErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleAllException(Exception ex, WebRequest request) {
        log.error("UNKNOWN_EXCEPTION_ERROR: ", ex);

        ErrorCode errorCode = CommonErrorCode.UNKNOWN_ERROR;

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse> handleApiException(ApiException ex, WebRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        ex.printStackTrace();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String errorMessage = fieldError.getDefaultMessage();

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(ErrorResponse.of(ex.getStatusCode(), errorMessage));
    }

}
