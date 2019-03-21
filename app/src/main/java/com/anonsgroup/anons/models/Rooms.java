package com.anonsgroup.anons.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Rooms {

    @Getter @Setter private String roomId;
    @Getter @Setter private String roomName;
    @Getter @Setter private int remainingMessage;
    @Getter @Setter private String senderId;


}
