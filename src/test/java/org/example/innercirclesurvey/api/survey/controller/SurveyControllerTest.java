package org.example.innercirclesurvey.api.survey.controller;

import org.example.innercirclesurvey.api.survey.dto.response.SurveyCreateResponse.SurveyFormCreateResponse;
import org.example.innercirclesurvey.api.survey.service.SurveyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = SurveyController.class)
class SurveyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SurveyService surveyService;

    @Nested
    @DisplayName("설문조사 생성")
    class SurveyCreateTest {
        @Test
        @DisplayName("설문조사 생성 성공")
        void createSurvey_success() throws Exception {
            // given
            SurveyFormCreateResponse response = SurveyFormCreateResponse.builder()
                    .surveyId(1L)
                    .build();
            Mockito.when(surveyService.createSurveyForm(any())).thenReturn(response);

            // when, then
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/survey")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content("""
                                                {
                                                  "title": "양식 제목",
                                                  "description": "양식 설명",
                                                  "items": [
                                                    {
                                                      "title": "단답형 항목 제목",
                                                      "description": "단답형 항목 설명",
                                                      "type": "SHORT_TEXT",
                                                      "required": true
                                                    }
                                                  ]
                                                }
                                            """
                                    )
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.surveyId", equalTo(1)));
        }
    }
}