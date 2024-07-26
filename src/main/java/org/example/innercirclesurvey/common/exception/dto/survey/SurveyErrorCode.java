package org.example.innercirclesurvey.common.exception.dto.survey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.innercirclesurvey.common.exception.dto.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum SurveyErrorCode implements ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "404", "설문조사 양식이 존재하지 않습니다."),
    VERSION_MISMATCH(HttpStatus.BAD_REQUEST.value(), "400", "설문조사 양식이 변경되었습니다. 다시 시도해주세요."),
    INVALID_ITEM_RESPONSE(HttpStatus.BAD_REQUEST.value(), "400", "설문조사 양식의 항목 응답이 상이합니다."),
    INVALID_SINGLE_CHOICE_RESPONSE(HttpStatus.BAD_REQUEST.value(), "400", "단일 선택형 답안에 여러 값이 포함되었습니다."),
    NOT_EXISTS_OPTION(HttpStatus.BAD_REQUEST.value(), "400", "설문조사 양식의 항목 옵션이 존재하지 않습니다.")
    ;

    private final int httpStatusCode;
    private final String errorCode;
    private final String errorMessage;
}
