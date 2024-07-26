package org.example.innercirclesurvey.common.exception.dto.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.innercirclesurvey.common.exception.dto.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500", "알 수 없는 오류가 발생하였습니다.");

    private final int httpStatusCode;
    private final String errorCode;
    private final String errorMessage;
}