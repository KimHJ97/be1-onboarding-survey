package org.example.innercirclesurvey.api.survey.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.innercirclesurvey.common.validator.EnumValue;
import org.example.innercirclesurvey.domain.survey.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
public class SurveyCreateRequest {

    @ToString
    @Getter
    @NoArgsConstructor
    public static class SurveyFormCreateRequest {
        @Size(max = 100, message = "설문조사 이름은 100자 이내입니다.")
        @NotBlank(message = "설문조사 이름은 필수 입력입니다.")
        private String title;

        @Size(max = 300, message = "설문조사 이름은 300자 이내입니다.")
        @NotBlank(message = "설문조사 설명은 필수 입력입니다.")
        private String description;

        @Valid
        @NotEmpty(message = "설문조사 항목은 필수 입력입니다.")
        private List<SurveyFormItemCreateRequest> items = new ArrayList<>();

        public SurveyForm toEntity(Survey survey) {
            return SurveyForm.builder()
                    .survey(survey)
                    .title(title)
                    .description(description)
                    .items(
                            items.stream()
                                    .map(SurveyFormItemCreateRequest::toEntity)
                                    .toList()
                    )
                    .build();
        }
    }

    @ToString
    @Getter
    @NoArgsConstructor
    public static class SurveyFormItemCreateRequest {
        @NotBlank(message = "항목 이름은 필수 입력입니다.")
        private String title;

        @NotBlank(message = "항목 설명은 필수 입력입니다.")
        private String description;

        @EnumValue(enumClass = SurveyFormItemType.class, message = "항목 타입이 올바르지 않습니다. {enumValues}")
        private String type;

        @NotNull(message = "항목 필수 여부는 필수 입력입니다.")
        private boolean required;

        private List<SurveyFormItemOptionCreateRequest> options = new ArrayList<>();

        public SurveyFormItem toEntity() {
            return SurveyFormItem.builder()
                    .title(title)
                    .description(description)
                    .type(SurveyFormItemType.valueOf(type))
                    .required(required)
                    .options(
                            options.stream()
                                    .map(SurveyFormItemOptionCreateRequest::toEntity)
                                    .toList()
                    )
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SurveyFormItemOptionCreateRequest {
        private String text;

        public SurveyFormItemOption toEntity() {
            return SurveyFormItemOption.builder()
                    .text(text)
                    .build();
        }
    }
}
