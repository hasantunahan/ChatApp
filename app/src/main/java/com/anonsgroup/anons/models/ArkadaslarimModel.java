package com.anonsgroup.anons.models;

public class ArkadaslarimModel {
    String username;
    String profilURL;

    public ArkadaslarimModel(String username, String profilURL, String receiverid, String durum) {
        this.username = username;
        this.profilURL = profilURL;
        this.uid = receiverid;
        this.durum = durum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    String uid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilURL() {
        return profilURL;
    }

    public void setProfilURL(String profilURL) {
        this.profilURL = profilURL;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    String durum ;

    public ArkadaslarimModel(String username, String profilURL, String durum) {
        this.username = username;
        this.profilURL = profilURL;
        this.durum = durum;
    }
    public ArkadaslarimModel(String username,String profilURL){
        this.username=username;
        this.profilURL=profilURL;
    }
}
