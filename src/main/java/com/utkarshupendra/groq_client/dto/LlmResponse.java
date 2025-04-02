package com.utkarshupendra.groq_client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LlmResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    private String systemFingerprint;
    private XGroq xGroq;

    @Data
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private LlmMessage message;
        private String finishReason;
    }

    @Data
    @NoArgsConstructor
    public static class Usage {
        private double queueTime;
        private int promptTokens;
        private double promptTime;
        private int completionTokens;
        private double completionTime;
        private int totalTokens;
        private double totalTime;
    }

    @Data
    @NoArgsConstructor
    public static class XGroq {
        private String id;
    }
}