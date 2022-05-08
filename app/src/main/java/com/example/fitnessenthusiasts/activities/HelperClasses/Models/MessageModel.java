package com.example.fitnessenthusiasts.activities.HelperClasses.Models;

public class MessageModel {

    private String msgID,content,senderID;
    private long time;

    public MessageModel() {
    }

    public MessageModel(String content, String senderID, long time) {
        this.content = content;
        this.senderID = senderID;
        this.time = time;
    }

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
