package org.example.innercirclesurvey.domain.survey;

import jakarta.persistence.*;
import lombok.*;
import org.example.innercirclesurvey.domain.common.BaseCreatedTimeEntity;

/**
 * 설문조사 양식 항목 선택옵션
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyFormItemOption extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formItemId")
    private SurveyFormItem formItem;

    @Column(length = 100, nullable = false)
    private String text;

    @Builder
    public SurveyFormItemOption(SurveyFormItem formItem, String text) {
        this.formItem = formItem;
        this.text = text;
    }
}
