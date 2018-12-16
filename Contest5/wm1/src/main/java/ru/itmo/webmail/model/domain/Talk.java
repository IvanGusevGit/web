package ru.itmo.webmail.model.domain;

public class Talk extends Item {
    private long sourceUserId;
    private long targetUserId;
    private String text;


    public long getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public String getSourceUserIdString() {
        return Long.toString(sourceUserId);
    }

    public void setSourceUserIdString(String sourceUserIdString) {
        setSourceUserId(Long.parseLong(sourceUserIdString));
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetUserIdString() {
        return Long.toString(targetUserId);
    }

    public void setTargetUserIdString(String targetUserIdString) {
        setTargetUserId(Long.parseLong(targetUserIdString));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextString() {
        return text;
    }

    public void setTextString(String textString) {
        setText(textString);
    }

}
