package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
public class Anonss {

    @Getter @Setter private String userId;
    @Getter @Setter private String location;
    @Getter @Setter private long date;
    @Getter @Setter private int countOfLike;
    @Getter @Setter private String text;

}
