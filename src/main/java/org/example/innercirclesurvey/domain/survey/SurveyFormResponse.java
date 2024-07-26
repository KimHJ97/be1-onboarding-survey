package org.example.innercirclesurvey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyFormResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formId")
    private SurveyForm surveyForm;

    @OneToMany(mappedBy = "surveyFormResponse", cascade = CascadeType.ALL)
    private List<SurveyFormItemResponse> surveyFormItemResponse;

    @Builder
    public SurveyFormResponse(SurveyForm surveyForm, List<SurveyFormItemResponse> surveyFormItemResponse) {
        this.surveyForm = surveyForm;
        this.surveyFormItemResponse = surveyFormItemResponse;
    }
}
