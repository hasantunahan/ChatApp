package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Anons {
    @Getter @Setter private int countOfLike;
    @Getter @Setter private long date;
    @Getter @Setter private String location;
    @Getter @Setter private String text;
    @Getter @Setter private String userId;
    @Getter @Setter private boolean answered = false;
    @Getter @Setter private boolean liked = false;
    @Getter @Setter private boolean seen = false;
    @Getter @Setter private String profilUrl;
    @Getter @Setter private String username;

}
