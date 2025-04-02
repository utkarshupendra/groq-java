package com.utkarshupendra.groq_client.exception;

import com.utkarshupendra.groq_client.dto.LlmRequest;
import com.utkarshupendra.groq_client.dto.LlmResponse;
import lombok.Getter;

@Getter
public class LlmServiceException extends RuntimeException {
    private final LlmRequest request;
    private final LlmResponse response;

    public LlmServiceException(LlmRequest request,
                               LlmResponse response,
                               String message) {
        super(message);
        this.request = request;
        this.response = response;
    }

}
