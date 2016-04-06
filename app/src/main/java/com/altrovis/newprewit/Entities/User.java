package com.altrovis.newprewit.Entities;

/**
 * Created by ricki on 4/5/2016.
 */
public class User {
    private String Nickname;
    private String UrlProfilPicture;

    public User(){}

    public User(String nickname, String urlProfilPicture) {
        Nickname = nickname;
        UrlProfilPicture = urlProfilPicture;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getUrlProfilPicture() {
        return UrlProfilPicture;
    }

    public void setUrlProfilPicture(String urlProfilPicture) {
        UrlProfilPicture = urlProfilPicture;
    }
}
