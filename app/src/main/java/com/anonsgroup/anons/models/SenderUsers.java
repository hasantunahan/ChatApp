package com.anonsgroup.anons.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class SenderUsers {

    @Getter @Setter private int userId;
    @Getter @Setter private String userName;
    @Getter @Setter private String name;
    @Getter @Setter private String surname;
    @Getter @Setter private byte[] senderPhoto;
}
