package org.example.innercirclesurvey.api.survey.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.innercirclesurvey.api.survey.dto.request.SurveyCreateRequest.SurveyFormCreateRequest;
import org.example.innercirclesurvey.api.survey.dto.request.SurveyResponseSubmitRequest.SurveyFormResponseSubmitRequest;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyCreateResponse.SurveyFormCreateResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyCreateResponse.SurveyFormModifyResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyResponseSearchResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyResponseSearchResponse.SurveyFormResponseSearchResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyResponseSubmitResponse;
import org.example.innercirclesurvey.api.survey.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 설문조사 API
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SurveyService surveyService;

    /**
     * 설문조사 생성
     *
     * @param createRequest 설문조사 생성 정보
     * @return
     */
    @PostMapping
    public ResponseEntity<SurveyFormCreateResponse> createSurveyForm(@RequestBody @Valid SurveyFormCreateRequest createRequest) {
        SurveyFormCreateResponse response = surveyService.createSurveyForm(createRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 설문조사 수정
     *
     * @param surveyId      설문조사 ID
     * @param createRequest 설문조사 수정 정보
     */
    @PutMapping("/{surveyId}")
    public ResponseEntity<?> modifySurveyForm(
            @PathVariable("surveyId") Long surveyId,
            @RequestBody @Valid SurveyFormCreateRequest createRequest) {
        SurveyFormModifyResponse response = surveyService.modifySurveyForm(surveyId, createRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 설문조사 응답 제출
     *
     * @param surveyId              설문조사 ID
     * @param responseSubmitRequest 설문조사 응답 정보
     * @return
     */
    @PostMapping("/{surveyId}/response/submit")
    public ResponseEntity<?> submitSurveyFormResponse(
            @PathVariable("surveyId") Long surveyId,
            @RequestBody @Valid SurveyFormResponseSubmitRequest responseSubmitRequest) {
        SurveyResponseSubmitResponse response = surveyService.submitSurveyFormResponse(surveyId, responseSubmitRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 설문조사 응답 조회
     *
     * @param surveyId   설문조사 ID
     * @param responseId 설문조사 응답 ID
     */
    @GetMapping("/{surveyId}/response")
    public ResponseEntity<?> searchSurveyFormResponse(
            @PathVariable("surveyId") Long surveyId,
            @RequestParam(required = false, name = "responseId") Long responseId) {
        List<SurveyFormResponseSearchResponse> response = surveyService.searchSurveyFormResponse(surveyId, responseId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
