package org.example.innercirclesurvey.repository.survey;

import org.example.innercirclesurvey.domain.survey.SurveyForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyFormRepository extends JpaRepository<SurveyForm, Long> {

    @Query("""
        SELECT form
        FROM SurveyForm form
        WHERE form.survey.id = :surveyId
    """)
    List<SurveyForm> findAllBySurveyId(Long surveyId);
}
