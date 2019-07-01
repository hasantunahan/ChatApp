package com.anonsgroup.anons.Notification;

import com.google.firebase.storage.StorageTask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Data {

    @Getter @Setter
    private String user;

    @Getter @Setter
    private int  icon;

    @Getter @Setter
    private String body;

    @Getter @Setter
    private String title;

    @Getter @Setter
    private String sented;

}
