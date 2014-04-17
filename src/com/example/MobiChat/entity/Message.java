package com.example.MobiChat.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 3/31/14
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class Message {
    private Long id;
    private Long fromUserId;
    private String fromUserName;
    private Long toUserId;
    private String toUserName;
    private String text;
    private Date dateMessage;

    public Message(Long id, Long fromUserId, String fromUserName, Long toUserId, String toUserName, String text, Date dateMessage) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.text = text;
        this.dateMessage = dateMessage;
    }

    public Message(Long id, Long fromUserId, Long toUserId, String text, Date dateMessage) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.text = text;
        this.dateMessage = dateMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(Date dateMessage) {
        this.dateMessage = dateMessage;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
