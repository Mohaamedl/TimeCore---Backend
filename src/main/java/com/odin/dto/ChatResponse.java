package com.odin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.odin.model.Message;


public class ChatResponse {
    private List<Choice> choices;
    public ChatResponse(List<Choice> choices) {
        this.choices = choices;
    }
    public List<Choice> getChoices() {
        return choices;
    }
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
    public static class Choice {
        private String id;
        private Message message;
        private int index;
        private String finishReason;

        public Choice(String id, Message message) {
            this.id = id;
            this.message = message;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public Message getMessage() {
            return message;
        }
        public void setMessage(Message message) {
            this.message = message;
        }
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }
        @JsonProperty("finish_reason")
        public String getFinishReason() {
            return finishReason;
        }
        @JsonProperty("finish_reason")
        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }
}
