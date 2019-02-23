package com.anonsgoup.anons.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class User {
    @Getter private String email;
    @Getter private String username;
    @Getter private String name;
    @Getter private String surname;
    @Getter private long dob;
    @Getter private String gender;
    @Getter private long createdDate;
    @Getter @Setter private String summInfo;
    @Getter @Setter private long lastDateOfLogOut;
    @Getter @Setter private long lastDateOfLogIn;
    @Getter @Setter private int countOfAnonsDaily;
    @Getter @Setter private int countOfAllAnons;

}
