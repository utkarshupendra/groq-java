package com.utkarshupendra.groq_client.api;

import com.utkarshupendra.groq_client.dto.LlmRequest;
import com.utkarshupendra.groq_client.dto.LlmResponse;
import com.utkarshupendra.groq_client.exception.LlmEmptyResponseException;
import com.utkarshupendra.groq_client.exception.LlmServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class LlmApiService {
    private final RestClient restClient;

    @Value("${llm.baseUrl:https://api.groq.com/openai/v1/}")
    private String llmBaseUrl;

    @Value("${llm.apiKey}")
    private String llmApiKey;

    @Value("${llm.subUrl.completion:chat/completions}")
    private String llmCompletionSubUrl;

    public LlmResponse getCompletion(LlmRequest request) {
        try {
            ResponseEntity<LlmResponse> responseEntity = restClient.post()
                    .uri(llmBaseUrl + llmCompletionSubUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + llmApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(request)
                    .retrieve()
                    .toEntity(LlmResponse.class);

            if (responseEntity.getStatusCode().isError()) {
                log.error("LLM service returned an error response {}", responseEntity.getBody());
                throw new LlmServiceException(request, responseEntity.getBody(), "LLM service returned an error response");
            }
            if (responseEntity.getBody() == null || Objects.isNull(responseEntity.getBody().getChoices()) || responseEntity.getBody().getChoices().isEmpty()) {
                log.error("LLM service returned an empty response {}", responseEntity.getBody());
                throw new LlmEmptyResponseException(request, responseEntity.getBody(), "LLM service returned an empty response");
            }
            return responseEntity.getBody();
        } catch (RuntimeException e) {
            throw new RuntimeException("Service Outage. Please try again.", e);
        }
    }
}
