package org.example.innercirclesurvey.domain.survey;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SurveyFormItemType {

    SHORT_TEXT("단답형"),
    LONG_TEXT("장문형"),
    SINGLE_CHOICE("단일 선택 리스트"),
    MULTIPLE_CHOICE("다중 선택 리스트");

    private final String typeName;
}
