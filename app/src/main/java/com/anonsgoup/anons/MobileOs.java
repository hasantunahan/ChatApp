package com.anonsgoup.anons;

public class MobileOs {
    public int getProfilImage() {
        return profilImage;
    }

    public void setProfilImage(int profilImage) {
        this.profilImage = profilImage;
    }

    public String getProfilName() {
        return profilName;
    }

    public void setProfilName(String profilName) {
        this.profilName = profilName;
    }

    public String getProfilKonum() {
        return profilKonum;
    }

    public void setProfilKonum(String profilKonum) {
        this.profilKonum = profilKonum;
    }

    public String getProfilYazi() {
        return profilYazi;
    }

    public void setProfilYazi(String profilYazi) {
        this.profilYazi = profilYazi;
    }

    public String getProfilTarih() {
        return profilTarih;
    }

    public void setProfilTarih(String profilTarih) {
        this.profilTarih = profilTarih;
    }

    public int getLikeimagee() {
        return Likeimagee;
    }

    public void setLikeimagee(int likeimagee) {
        Likeimagee = likeimagee;
    }

    public String getLikeSayisi() {
        return LikeSayisi;
    }

    public void setLikeSayisi(String likeSayisi) {
        LikeSayisi = likeSayisi;
    }

    int profilImage;
    String profilName;

    public MobileOs(int profilImage, String profilName, String profilKonum, String profilYazi, String profilTarih, int likeimagee, String likeSayisi) {
        this.profilImage = profilImage;
        this.profilName = profilName;
        this.profilKonum = profilKonum;
        this.profilYazi = profilYazi;
        this.profilTarih = profilTarih;
        Likeimagee = likeimagee;
        LikeSayisi = likeSayisi;
    }

    String profilKonum;
    String profilYazi;
    String profilTarih;
    int Likeimagee;
    String LikeSayisi;
}
