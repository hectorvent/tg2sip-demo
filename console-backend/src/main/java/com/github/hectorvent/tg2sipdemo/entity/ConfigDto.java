package com.github.hectorvent.tg2sipdemo.entity;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ConfigDto {

    public boolean showAsPublic;
    public Long id;
    public String name;
    public String username;
    public String phone;
    public String defaultSound;


    public static final ConfigDto mappper(User user){

        ConfigDto cdto = new ConfigDto();

        if (user != null){
            cdto.showAsPublic = user.showAsPublic;
            cdto.defaultSound = user.defaultSound;
            cdto.id = user.telegramId;
            cdto.name = user.telegramName;
            cdto.username = user.telegramUsername;
            cdto.phone = user.telegramPhone;
        }

        return cdto;
    }
}