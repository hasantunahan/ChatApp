package com.anonsgroup.anons;

import java.util.ArrayList;

public class NavigationDrawerItem {
    String baslik;
    int resimID;

    public String getBaslik() {
        return baslik;
    }

    public int getResimID() {
        return resimID;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public void setResimID(int resimID) {
        this.resimID = resimID;
    }

    public static ArrayList<NavigationDrawerItem> getData(){
        ArrayList<NavigationDrawerItem> dataList=new ArrayList<NavigationDrawerItem>();
        int[] resimIDs=getImages();
        String[] basliklar=getTitles();

        for (int i=0; i<basliklar.length;i++){
            NavigationDrawerItem navItem = new NavigationDrawerItem();
            navItem.setBaslik(basliklar[i]);
            navItem.setResimID(resimIDs[i]);
            dataList.add(navItem);
        }
        return dataList;
    }

    private static String[] getTitles() {
        return new String[]{
                "Engellenenler","Beğendiğin Anonslar","Uygulamayı Paylaş","Lisanslar","Gizlilik Koşulları","Görüş ve Öneriler","Yardım","Ayarlar"
        };
    }

    private static int[] getImages() {
        return new int[]{
                R.drawable.ic_like,
                R.drawable.ic_like,
                R.drawable.ic_like,
                R.drawable.ic_like,
                R.drawable.ic_like,
                R.drawable.ic_like,
                R.drawable.ic_like,
                R.drawable.ic_like};

    }


}
