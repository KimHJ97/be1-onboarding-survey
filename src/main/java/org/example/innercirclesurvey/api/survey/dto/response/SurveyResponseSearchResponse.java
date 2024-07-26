package org.example.innercirclesurvey.api.survey.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.innercirclesurvey.domain.survey.SurveyFormResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class SurveyResponseSearchResponse {

    @Getter
    @NoArgsConstructor
    public static class SurveyFormResponseSearchResponse {

        private Long formId;

        List<SurveyFormItemResponseSearchResponse> items;

        @Builder
        public SurveyFormResponseSearchResponse(Long formId, List<SurveyFormItemResponseSearchResponse> items) {
            this.formId = formId;
            this.items = items;
        }

        public static SurveyFormResponseSearchResponse fromSurveyFormResponse(SurveyFormResponse surveyFormResponse) {
            return SurveyFormResponseSearchResponse.builder()
                    .formId(surveyFormResponse.getSurveyForm().getId())
                    .items(
                            surveyFormResponse.getSurveyFormItemResponse().stream().map(
                                    item -> {
                                        return SurveyFormItemResponseSearchResponse.builder()
                                                .answer(item.getAnswer())
                                                .itemId(item.getSurveyFormItem().getId())
                                                .build();
                                    }
                            ).toList()
                    )
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SurveyFormItemResponseSearchResponse {
        private Long itemId;
        private String answer;

        @Builder
        public SurveyFormItemResponseSearchResponse(Long itemId, String answer) {
            this.itemId = itemId;
            this.answer = answer;
        }
    }
}
