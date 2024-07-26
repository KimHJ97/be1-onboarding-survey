package org.example.innercirclesurvey.domain.survey;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.innercirclesurvey.domain.common.BaseEntity;
import org.springframework.util.ObjectUtils;

import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyForm> forms;

    @OneToOne
    @JoinColumn(name = "latest_version_id")
    private SurveyForm latestVersion;

    @Version
    private Long version;

    public void updateLatestVersion(SurveyForm surveyForm) {
        this.latestVersion = surveyForm;
    }

    public boolean validateLatestVersion(Long formId) {
        if (ObjectUtils.isEmpty(formId)) {
            return false;
        }

        return formId.compareTo(latestVersion.getId()) == 0;
    }
}
