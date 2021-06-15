package com.task.data.remote.errorhandler;

public class HttpResponse {

    private int statusCode;
    private String statusTitle;
    private String statusMessage;
    private String statusDescription;

    public HttpResponse() {
        this.statusCode = 0;
        this.statusTitle = "";
        this.statusMessage = "";
        this.statusDescription = "";
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusTitle() {
        return statusTitle;
    }

    public void setStatusTitle(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
