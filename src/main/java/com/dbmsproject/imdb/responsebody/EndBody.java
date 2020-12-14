package com.dbmsproject.imdb.responsebody;

public class EndBody {
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EndBody() {
    }

    public EndBody(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
