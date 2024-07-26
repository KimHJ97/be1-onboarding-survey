package org.example.innercirclesurvey.repository.survey;

import org.example.innercirclesurvey.domain.survey.SurveyForm;
import org.example.innercirclesurvey.domain.survey.SurveyFormResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyFormResponseRepository extends JpaRepository<SurveyFormResponse, Long> {

    @Query("""
        SELECT formResponse
        FROM SurveyFormResponse formResponse
            JOIN FETCH formResponse.surveyFormItemResponse
        WHERE formResponse.surveyForm.id IN :formIds
    """)
    List<SurveyFormResponse> findFormWithItemResponseByFormIds(@Param("formIds") List<Long> formIds);
}
