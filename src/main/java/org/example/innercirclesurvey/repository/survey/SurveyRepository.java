package org.example.innercirclesurvey.repository.survey;

import org.example.innercirclesurvey.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    @Query("""
        SELECT survey
        FROM Survey survey
            JOIN FETCH survey.forms
        WHERE survey.id = :id
    """)
    Optional<Survey> findSurveyWithFormsBySurveyId(Long id);

    @Query("""
        SELECT survey
        FROM Survey survey
            JOIN FETCH survey.forms
        WHERE survey.id = :id
    """)
    Optional<Survey> findSurveyWithLatestVersionBySurveyId(Long id);
}
