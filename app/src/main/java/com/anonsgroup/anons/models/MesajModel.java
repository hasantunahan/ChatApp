package com.anonsgroup.anons.models;

public class MesajModel {
    private String message;
    private String sender;
    private String receiver;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSeend() {
        return seend;
    }

    public void setSeend(boolean seend) {
        this.seend = seend;
    }

    private boolean seend;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public MesajModel(String message, String sender, String receiver, long time, boolean seend) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.seend = seend;
    }

    public MesajModel(){

}
}
