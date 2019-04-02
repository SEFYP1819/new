package com.example.fyp;

public class Message {

    private boolean isMine;
    private  String Content, Time;

    public Message(boolean isMine, String content, String time) {
        this.isMine = isMine;
        Content = content;
        Time = time;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
