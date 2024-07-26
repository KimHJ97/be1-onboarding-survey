package org.example.innercirclesurvey.api.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.example.innercirclesurvey.api.survey.dto.request.SurveyCreateRequest.SurveyFormCreateRequest;
import org.example.innercirclesurvey.api.survey.dto.request.SurveyResponseSubmitRequest.SurveyFormItemResponseSubmitRequest;
import org.example.innercirclesurvey.api.survey.dto.request.SurveyResponseSubmitRequest.SurveyFormResponseSubmitRequest;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyCreateResponse.SurveyFormCreateResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyCreateResponse.SurveyFormModifyResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyResponseSearchResponse.SurveyFormResponseSearchResponse;
import org.example.innercirclesurvey.api.survey.dto.response.SurveyResponseSubmitResponse;
import org.example.innercirclesurvey.common.exception.ApiException;
import org.example.innercirclesurvey.common.exception.dto.survey.SurveyErrorCode;
import org.example.innercirclesurvey.domain.survey.*;
import org.example.innercirclesurvey.repository.survey.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyFormRepository surveyFormRepository;
    private final SurveyFormItemRepository surveyFormItemRepository;
    private final SurveyFormResponseRepository surveyFormResponseRepository;
    private final SurveyFormItemResponseRepository surveyFormItemResponseRepository;

    /**
     * 설문조사 양식과 설문조사 항목(항목+선택옵션)을 생성한다.
     *
     * @param createRequest 설문조사 생성 정보
     * @return
     */
    @Transactional
    public SurveyFormCreateResponse createSurveyForm(SurveyFormCreateRequest createRequest) {
        // 설문조사 양식 관리 생성
        Survey savedSurvey = surveyRepository.save(new Survey());

        // 설문조사 양식 + 설문조사 항목(항목+선택옵션) 생성
        SurveyForm savedForm = surveyFormRepository.save(createRequest.toEntity(savedSurvey));

        // 설문조사 양식 관리 -> 양식 버전 업데이트
        savedSurvey.updateLatestVersion(savedForm);

        return SurveyFormCreateResponse.of(savedSurvey.getId());
    }

    /**
     * 새로운 버전의 설문조사 양식과 설문조사 항목(항목+선택옵션)을 생성한다.
     *
     * @param surveyId      설문조사 ID
     * @param createRequest 설문조사 생성 정보
     * @return
     */
    @Transactional
    public SurveyFormModifyResponse modifySurveyForm(Long surveyId, SurveyFormCreateRequest createRequest) {
        // 설문조사 관리 조회
        Survey findSurvey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ApiException(SurveyErrorCode.NOT_FOUND));

        // 설문조사 양식 + 설문조사 항목(항목+선택옵션) 생성
        SurveyForm savedForm = surveyFormRepository.save(createRequest.toEntity(findSurvey));

        // 설문조사 양식 관리 -> 양식 버전 업데이트
        findSurvey.updateLatestVersion(savedForm);

        return SurveyFormModifyResponse.of(findSurvey.getId());
    }

    /**
     * 설문조사 응답 제출 정보를 등록한다.
     *
     * @param surveyId
     * @param responseSubmitRequest
     */
    @Transactional
    public SurveyResponseSubmitResponse submitSurveyFormResponse(Long surveyId, SurveyFormResponseSubmitRequest responseSubmitRequest) {
        // 설문조사 관리 조회
        Survey findSurvey = surveyRepository.findSurveyWithLatestVersionBySurveyId(surveyId)
                .orElseThrow(() -> new ApiException(SurveyErrorCode.NOT_FOUND));

        // 설문조사 양식 변경 여부 체크
        if (!findSurvey.validateLatestVersion(responseSubmitRequest.getFormId())) {
            throw new ApiException(SurveyErrorCode.VERSION_MISMATCH);
        }

        // 설문조사 양식 응답 등록
        SurveyForm currentSurveyForm = findSurvey.getLatestVersion();
        SurveyFormResponse savedSurveyFormResponse = surveyFormResponseRepository.save(
                responseSubmitRequest.toEntity(currentSurveyForm)
        );

        // 설문조사 양식 항목별 응답 목록 등록
        List<SurveyFormItemResponseSubmitRequest> itemResponseList = responseSubmitRequest.getItems();
        for (SurveyFormItemResponseSubmitRequest itemResponse : itemResponseList) {
            SurveyFormItem findSurveyFormItem = surveyFormItemRepository.findItemWithItemOptionsByItemId(itemResponse.getItemId())
                    .orElseThrow(() -> new ApiException(SurveyErrorCode.INVALID_ITEM_RESPONSE));

            validateItemResponseByTypeOrThrow(findSurveyFormItem, itemResponse);

            surveyFormItemResponseRepository.save(
                    itemResponse.toEntity(savedSurveyFormResponse, findSurveyFormItem)
            );
        }

        return SurveyResponseSubmitResponse.of(savedSurveyFormResponse.getId());
    }

    public void validateItemResponseByTypeOrThrow(SurveyFormItem formItem, SurveyFormItemResponseSubmitRequest itemResponse) {
        SurveyFormItemType type = formItem.getType();
        String[] options = itemResponse.getAnswer().split(",");

        if (ObjectUtils.isEmpty(options)) {
            throw new ApiException(SurveyErrorCode.INVALID_ITEM_RESPONSE);
        }

        switch (type) {
            case SHORT_TEXT, LONG_TEXT -> {
                return;
            }
            case SINGLE_CHOICE -> {
                if (options.length > 1) {
                    throw new ApiException(SurveyErrorCode.INVALID_SINGLE_CHOICE_RESPONSE);
                }
            }
        }

        // 단일 선택 리스트(SINGLE_CHOICE), 다중 선택 리스트(MULTIPLE_CHOICE)인 경우 Option에 존재하는지 검증
        for (String option : options) {
            if (!NumberUtils.isCreatable(option)) {
                throw new ApiException(SurveyErrorCode.NOT_EXISTS_OPTION);
            }

            long optionId = Long.parseLong(option);
            if (!formItem.isExistsOption(optionId)) {
                throw new ApiException(SurveyErrorCode.NOT_EXISTS_OPTION);
            }
        }
    }

    @Transactional
    public List<SurveyFormResponseSearchResponse> searchSurveyFormResponse(Long surveyId, Long responseId) {
        // 설문조사 관리 조회
        Survey survey = surveyRepository.findSurveyWithFormsBySurveyId(surveyId)
                .orElseThrow(() -> new ApiException(SurveyErrorCode.NOT_FOUND));

        // 설문조사 양식 ID 목록 조회
        List<Long> formIds = survey.getForms().stream().map(SurveyForm::getId).toList();

        // 양식들에 해당하는 응답(양식 응답 + 항목 응답) 목록 조회
        List<SurveyFormResponse> surveyFormResponses = surveyFormResponseRepository.findFormWithItemResponseByFormIds(formIds);

        return surveyFormResponses.stream()
                .map(SurveyFormResponseSearchResponse::fromSurveyFormResponse)
                .toList();
    }
}
