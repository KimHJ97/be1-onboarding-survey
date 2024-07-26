package org.example.innercirclesurvey.common.exception.dto;

public interface ErrorCode<T> {

    int getHttpStatusCode();
    String getErrorCode();
    String getErrorMessage();

}
