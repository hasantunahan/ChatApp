package com.anonsgroup.anons.models;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class User extends FirebaseUserModel {


    public User(String profilUrl, String backgroundUrl,String email, String username, String name, String surname, long dob, String gender, long createdDate, String summInfo, long lastDateOfLogOut, long lastDateOfLogIn, int countOfAnonsDaily, int countOfAllAnons) {
        super(profilUrl,backgroundUrl,email,username,name,surname,dob,gender,createdDate,summInfo,lastDateOfLogOut,lastDateOfLogIn,countOfAnonsDaily,countOfAllAnons);
    }
    @Getter @Setter private byte[] profilPhoto;
    @Getter @Setter private byte[] profilBackground;

    public FirebaseUserModel getFirebaseModel() {
        return new FirebaseUserModel(getProfilUrl(),getBackgroundUrl(),getEmail(),getUsername(),getName(),getSurname(),getDob(),getGender(),getCreatedDate(),getSummInfo(),getLastDateOfLogOut(),getLastDateOfLogIn(),getCountOfAnonsDaily(),getCountOfAllAnons());
    }
}
