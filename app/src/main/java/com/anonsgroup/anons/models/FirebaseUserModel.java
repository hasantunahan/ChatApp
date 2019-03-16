package com.anonsgroup.anons.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class FirebaseUserModel {
    @Getter
    @Setter
    private String email;
    @Getter @Setter private String username;
    @Getter @Setter private String name;
    @Getter @Setter private String surname;
    @Getter @Setter private long dob;
    @Getter @Setter private String gender;
    @Getter private long createdDate;
    @Getter @Setter private String summInfo;
    @Getter @Setter private long lastDateOfLogOut;
    @Getter @Setter private long lastDateOfLogIn;
    @Getter @Setter private int countOfAnonsDaily;
    @Getter @Setter private int countOfAllAnons;

}
