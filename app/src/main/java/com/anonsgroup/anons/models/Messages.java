package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Messages {

    @Getter @Setter private String messageId;
    @Getter @Setter private String roomId;
    @Getter @Setter private String username;
    @Getter @Setter private String messageText;
    @Getter @Setter private String date;

}
