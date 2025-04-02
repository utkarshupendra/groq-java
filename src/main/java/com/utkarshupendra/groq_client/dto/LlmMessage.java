package com.utkarshupendra.groq_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LlmMessage {
    private String role;
    private String content;
}
