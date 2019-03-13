package com.anonsgroup.anons.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class User {
    public User(String email, String username, String name, String surname, long dob, String gender, long createdDate, String summInfo, long lastDateOfLogOut, long lastDateOfLogIn, int countOfAnonsDaily, int countOfAllAnons) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.gender = gender;
        this.createdDate = createdDate;
        this.summInfo = summInfo;
        this.lastDateOfLogOut = lastDateOfLogOut;
        this.lastDateOfLogIn = lastDateOfLogIn;
        this.countOfAnonsDaily = countOfAnonsDaily;
        this.countOfAllAnons = countOfAllAnons;
    }
    @Getter @Setter private byte[] profilPhoto;
    @Getter @Setter private byte[] profilBackground;
    @Getter @Setter private String email;
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
