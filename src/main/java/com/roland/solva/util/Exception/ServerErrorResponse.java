package com.roland.solva.util.Exception;

/**
 * @author Roland Pilpani 13.09.2022
 */
public class ServerErrorResponse {
    private String message;
    private long timestamp;

    public ServerErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String Message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
