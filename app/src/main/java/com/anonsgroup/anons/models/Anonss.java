package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class Anonss {

    @Getter @Setter private int anonsId;
    @Getter @Setter private int userId;
    @Getter @Setter private String location;
    @Getter @Setter private String date;
    @Getter @Setter private int countOfLike;
    @Getter @Setter private int seen;
    @Getter @Setter private String text;

}
