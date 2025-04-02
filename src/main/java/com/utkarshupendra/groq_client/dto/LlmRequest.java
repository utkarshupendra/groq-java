package com.utkarshupendra.groq_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LlmRequest {
    private String model;
    private List<LlmMessage> messages;
}
