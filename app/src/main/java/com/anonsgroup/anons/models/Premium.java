package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class Premium {

    @Getter @Setter private String premiumName;
    @Getter @Setter private byte[] image;
    @Getter @Setter private int price;
    @Getter @Setter private String property;
}
