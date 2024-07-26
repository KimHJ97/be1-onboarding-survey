package org.example.innercirclesurvey.api.survey.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.innercirclesurvey.domain.survey.SurveyForm;
import org.example.innercirclesurvey.domain.survey.SurveyFormItem;
import org.example.innercirclesurvey.domain.survey.SurveyFormItemResponse;
import org.example.innercirclesurvey.domain.survey.SurveyFormResponse;

import java.util.ArrayList;
import java.util.List;

public class SurveyResponseSubmitRequest {

    @Getter
    @NoArgsConstructor
    public static class SurveyFormResponseSubmitRequest {

        @NotNull(message = "설문조사 양식 ID는 필수 입력입니다.")
        private Long formId;

        @NotEmpty(message = "설문조사 항목 응답 내역이 존재하지 않습니다.")
        List<SurveyFormItemResponseSubmitRequest> items = new ArrayList<>();

        public SurveyFormResponse toEntity(SurveyForm surveyForm) {
            return SurveyFormResponse.builder()
                    .surveyForm(surveyForm)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SurveyFormItemResponseSubmitRequest {
        @NotNull(message = "설문조사 항목 ID는 필수 입력입니다.")
        private Long itemId;

        @NotBlank(message = "설문조사 항목 응답값을 입력해주세요.")
        private String answer;

        public SurveyFormItemResponse toEntity(SurveyFormResponse formResponse, SurveyFormItem formItem) {
            return SurveyFormItemResponse.builder()
                    .surveyFormResponse(formResponse)
                    .surveyFormItem(formItem)
                    .answer(answer)
                    .build();
        }
    }
}
