package com.github.hectorvent.tg2sipdemo.telegram;

public class TelegramAuthData {

    public Long id;
    public String firstName;
    public String lastName;
    public String username;
    public String hash;
    public Long authDate;
    public String photoUrl;

    public String getFullName() {
        return firstName + ' ' + lastName;
    }

    @Override
    public String toString() {
        return "TelegramAuthData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", hash='" + hash + '\'' +
                ", authDate=" + authDate +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
