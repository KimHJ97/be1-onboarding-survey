package org.example.innercirclesurvey.api.survey.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyCreateResponse {

    @Getter
    @NoArgsConstructor
    public static class SurveyFormCreateResponse {
        private Long surveyId;

        @Builder
        public SurveyFormCreateResponse(Long surveyId) {
            this.surveyId = surveyId;
        }

        public static SurveyFormCreateResponse of(Long surveyId) {
            return SurveyFormCreateResponse.builder()
                    .surveyId(surveyId)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SurveyFormModifyResponse {
        private Long surveyId;

        @Builder
        public SurveyFormModifyResponse(Long surveyId) {
            this.surveyId = surveyId;
        }

        public static SurveyFormModifyResponse of(Long surveyId) {
            return SurveyFormModifyResponse.builder()
                    .surveyId(surveyId)
                    .build();
        }
    }

}
