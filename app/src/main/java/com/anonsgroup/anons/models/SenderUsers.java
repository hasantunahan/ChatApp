package com.anonsgroup.anons.models;


import android.graphics.BitmapFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class SenderUsers {
    //TODO : int olarak aldım byte çevirilecek
    public SenderUsers(String userName, String name, String surname, int senderPhoto) {
        this.userName = userName;
        this.name = name;
        this.surname = surname;
        this.senderPhoto = senderPhoto;
    }

    @Getter @Setter private int userId;
    @Getter @Setter private String userName;
    @Getter @Setter private String name;
    @Getter @Setter private String surname;
    @Getter @Setter private int senderPhoto;

}
