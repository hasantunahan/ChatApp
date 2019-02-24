package com.anonsgoup.anons;

public class AnaEkranitem {
    int AnaprofilImage,anaYeniBildiriImage;
    String anaKisi;
    String anaYazi;
    String anaKonum;
    String anaTarih;

    public AnaEkranitem(int anaprofilImage, int anaYeniBildiriImage, String anaKisi, String anaYazi, String anaKonum, String anaTarih) {
        AnaprofilImage = anaprofilImage;
        this.anaYeniBildiriImage = anaYeniBildiriImage;
        this.anaKisi = anaKisi;
        this.anaYazi = anaYazi;
        this.anaKonum = anaKonum;
        this.anaTarih = anaTarih;
    }


    public int getAnaprofilImage() {
        return AnaprofilImage;
    }

    public void setAnaprofilImage(int anaprofilImage) {
        AnaprofilImage = anaprofilImage;
    }

    public int getAnaYeniBildiriImage() {
        return anaYeniBildiriImage;
    }

    public void setAnaYeniBildiriImage(int anaYeniBildiriImage) {
        this.anaYeniBildiriImage = anaYeniBildiriImage;
    }

    public String getAnaKisi() {
        return anaKisi;
    }

    public void setAnaKisi(String anaKisi) {
        this.anaKisi = anaKisi;
    }

    public String getAnaYazi() {
        return anaYazi;
    }

    public void setAnaYazi(String anaYazi) {
        this.anaYazi = anaYazi;
    }

    public String getAnaKonum() {
        return anaKonum;
    }

    public void setAnaKonum(String anaKonum) {
        this.anaKonum = anaKonum;
    }

    public String getAnaTarih() {
        return anaTarih;
    }

    public void setAnaTarih(String anaTarih) {
        this.anaTarih = anaTarih;
    }





}
