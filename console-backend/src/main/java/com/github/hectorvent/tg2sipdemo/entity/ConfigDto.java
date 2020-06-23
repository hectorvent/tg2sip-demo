package com.github.hectorvent.tg2sipdemo.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ConfigDto {

    public boolean showAsPublic;
    public Long id;
    public String name;
    public String username;
    public String phone;
    public String photo;
    public String defaultSound;
    public String botName;

    public static final ConfigDto create(String botName){
        ConfigDto cdto = new ConfigDto();
        cdto.name = "Anonymous";
        cdto.username = "anonymous";
        cdto.phone = "8888888888";
        cdto.botName = botName;
        cdto.id = -1L;

        return cdto;
    }


    public void setUserProperties(User user){

        if (user == null){
            return;
        }

        showAsPublic = user.showAsPublic;
        defaultSound = user.defaultSound;
        id = user.telegramId;
        name = user.telegramName;
        username = user.telegramUsername;
        phone = user.telegramPhone;
        photo = user.telegramPhoto;
    }
}
