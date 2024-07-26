package org.example.innercirclesurvey.domain.survey;

import jakarta.persistence.*;
import lombok.*;
import org.example.innercirclesurvey.domain.common.BaseCreatedTimeEntity;
import org.example.innercirclesurvey.domain.common.BaseEntity;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 설문조사 양식 항목
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SurveyFormItem extends BaseCreatedTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "formId")
    private SurveyForm form;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private SurveyFormItemType type;

    @Column(nullable = false)
    private boolean required;

    @OneToMany(mappedBy = "formItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SurveyFormItemOption> options;

    @Builder
    public SurveyFormItem(SurveyForm form, String title, String description, SurveyFormItemType type, boolean required, List<SurveyFormItemOption> options) {
        this.form = form;
        this.title = title;
        this.description = description;
        this.type = type;
        this.required = required;
        if (!ObjectUtils.isEmpty(options)) {
            this.options = options;
            options.forEach(option -> option.setFormItem(this));
        }
    }

    public boolean isExistsOption(Long optionId) {
        if (ObjectUtils.isEmpty(options)) {
            return false;
        }

        return this.options.stream()
                .anyMatch(option -> option.getId().equals(optionId));
    }
}
