package com.github.hectorvent.tg2sipdemo.telegram;


public class TelegramAuthData {

    public Long id;
    public String firstName;
    public String lastName;
    public String username;
    public String hash;
    public Long authDate;
    public String photoUrl;


    public String getFullName(){
        return firstName+' '+lastName;
    }

}