package com.example.chatapp.Models;

public class Chat {
    private String sender;
    private String receiver;

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public Chat(String sender, String receiver, boolean isseen, String mesaj) {
        this.sender = sender;
        this.receiver = receiver;
        this.isseen = isseen;
        this.mesaj = mesaj;
    }

    private boolean isseen;

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

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    private String mesaj;

    public Chat(){

    }

}
