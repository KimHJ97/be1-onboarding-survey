package org.example.innercirclesurvey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyFormItemResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responseId")
    private SurveyFormResponse surveyFormResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private SurveyFormItem surveyFormItem;

    @Column(length = 100, nullable = false)
    private String answer;

    @Builder
    public SurveyFormItemResponse(SurveyFormResponse surveyFormResponse, SurveyFormItem surveyFormItem, String answer) {
        this.surveyFormResponse = surveyFormResponse;
        this.surveyFormItem = surveyFormItem;
        this.answer = answer;
    }
}
