package com.example.showtime;

import java.util.Date;
import java.util.Map;

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;


    public ChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(Map params){

        this.setMessageText((String) params.get("messageText"));
        this.setMessageUser((String) params.get("messageUser"));
        this.setMessageTime((Long) params.get("messageTime"));

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
