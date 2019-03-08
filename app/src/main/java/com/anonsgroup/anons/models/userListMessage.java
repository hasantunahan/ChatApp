package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class userListMessage{

    @Getter @Setter private String messageId;
    @Getter @Setter private String odaId;
    @Getter @Setter private String kisiId;
    @Getter @Setter private String mesajMetni;
    @Getter @Setter private String zaman;
    @Getter @Setter private byte[] profilPhoto;



}
