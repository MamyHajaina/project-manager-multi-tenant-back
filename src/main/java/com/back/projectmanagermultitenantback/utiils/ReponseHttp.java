package com.back.projectmanagermultitenantback.utiils;

public class ReponseHttp {

    private boolean success;
    private String errorMessages;
    private String messages;
    private Object data;

    public ReponseHttp() {
    }


    public ReponseHttp(boolean success, String errorMessages, String messages, Object data) {
        super();
        this.success = success;
        this.errorMessages = errorMessages;
        this.messages = messages;
        this.data = data;
    }


    public String getMessages() {
        return messages;
    }


    public void setMessages(String messages) {
        this.messages = messages;
    }


    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getErrorMessages() {
        return errorMessages;
    }
    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }



}
