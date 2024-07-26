package org.example.innercirclesurvey.repository.survey;

import org.example.innercirclesurvey.domain.survey.SurveyFormItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SurveyFormItemRepository extends JpaRepository<SurveyFormItem, Long> {

    @Query("""
        SELECT item
        FROM SurveyFormItem item
            LEFT JOIN FETCH item.options options
        WHERE item.id = :itemId
    """)
    Optional<SurveyFormItem> findItemWithItemOptionsByItemId(Long itemId);
}
