package com.sololearner.chatapp.core;

import java.util.Date;

public class MessageModel {
    private String message;
    private String sender;
    private String senderId;
    private long messageTime;

    public MessageModel() { }

    public MessageModel(String message, String sender, String senderId) {
        this.message = message;
        this.sender = sender;
        messageTime = new Date().getTime();
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
