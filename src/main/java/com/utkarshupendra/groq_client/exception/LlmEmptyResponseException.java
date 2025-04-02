package com.utkarshupendra.groq_client.exception;

import com.utkarshupendra.groq_client.dto.LlmRequest;
import com.utkarshupendra.groq_client.dto.LlmResponse;

public class LlmEmptyResponseException extends LlmServiceException {

    public LlmEmptyResponseException(LlmRequest request, LlmResponse response, String message) {
        super(request, response, message);
    }
}
