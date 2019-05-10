package com.example.fyp;

public class riskAssessMessage {

    private String MessageContent, Time;
    private Boolean isMine;

    public riskAssessMessage(String messageContent, String time, Boolean isMine) {
        MessageContent = messageContent;
        Time = time;
        this.isMine = isMine;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public Boolean isMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
