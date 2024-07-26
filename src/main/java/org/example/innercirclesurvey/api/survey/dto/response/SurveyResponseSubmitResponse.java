package org.example.innercirclesurvey.api.survey.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SurveyResponseSubmitResponse {
    private Long responseId;

    @Builder
    public SurveyResponseSubmitResponse(Long responseId) {
        this.responseId = responseId;
    }

    public static SurveyResponseSubmitResponse of(Long responseId) {
        return SurveyResponseSubmitResponse.builder()
                .responseId(responseId)
                .build();
    }
}
