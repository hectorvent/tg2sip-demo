package com.github.hectorvent.tg2sipdemo.entity;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CdrDto {

    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

    public Long id;
    public String callType;
    public String callid;
    public String date;
    public long duration;
    public String username;
    public String name;
    public User user;
    public boolean mine;


    public static final CdrDto mappper(Cdr cdr, String username){

        CdrDto cdto = new CdrDto();
        cdto.callType = cdr.callType;
        cdto.callid  = cdr.callid;
        cdto.date = DTF.format(cdr.startDate);
        cdto.duration = ChronoUnit.SECONDS.between(cdr.startDate, cdr.endDate);

        cdto.mine = cdr.user.telegramUsername.equals(username);

        if (cdto.user.showAsPublic || cdto.mine){
            cdto.name = cdr.user.telegramName;
            cdto.username = cdr.user.telegramUsername;
        } else {
            cdto.name = "Anonymous";
            cdto.username = "anonymous";
        }
        return cdto;
    }

}