package org.example.innercirclesurvey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.innercirclesurvey.domain.common.BaseCreatedTimeEntity;
import org.example.innercirclesurvey.domain.common.BaseEntity;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 설문조사 양식
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyForm extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surveyId")
    private Survey survey;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<SurveyFormItem> items;

    @OneToMany(mappedBy = "surveyForm")
    private List<SurveyFormResponse> responses;

    @Builder
    public SurveyForm(Survey survey, String title, String description, List<SurveyFormItem> items) {
        this.survey = survey;
        this.title = title;
        this.description = description;
        if (!ObjectUtils.isEmpty(items)) {
            this.items = items;
            items.forEach(item -> item.setForm(this));
        }
    }
}
