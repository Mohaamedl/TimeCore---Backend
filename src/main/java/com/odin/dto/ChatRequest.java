package com.odin.dto;


import java.util.ArrayList;
import java.util.List;

import com.odin.model.Message;

public class ChatRequest {
    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;
    private boolean store = true;  // Add this field

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
        this.n = 1;  // Set default value
        this.temperature = 0.7;  // Set default temperature
        this.store = true;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public boolean isStore() {
        return store;
    }
    public void setStore(boolean store) {
        this.store = store;
    }
}
