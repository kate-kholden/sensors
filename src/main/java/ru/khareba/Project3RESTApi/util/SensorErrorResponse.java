package ru.khareba.Project3RESTApi.util;

public class SensorErrorResponse {
    private String message;
    private long tameStamp;

    public SensorErrorResponse(String message, long tameStamp) {
        this.message = message;
        this.tameStamp = tameStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTameStamp() {
        return tameStamp;
    }

    public void setTameStamp(long tameStamp) {
        this.tameStamp = tameStamp;
    }
}
