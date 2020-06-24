package com.github.hectorvent.tg2sipdemo.entity;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

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
    public boolean mine;


    public static final CdrDto mappper(Cdr cdr, Optional<Long> telegramId){

        CdrDto cdto = new CdrDto();
        cdto.callType = cdr.callType;
        cdto.callid  = cdr.callid;
        cdto.date = DTF.format(cdr.startDate);

        telegramId.ifPresent(id-> cdto.mine = cdr.user.telegramId.equals(id));

        if (cdr.user.showAsPublic || cdto.mine){
            cdto.name = cdr.user.telegramName;
            cdto.username = cdr.user.telegramUsername;
        } else {
            cdto.name = "Anonymous";
            cdto.username = "anonymous";
        }

        // if the endDate is null means the call is in process.
        if (cdr.endDate != null){
            cdto.duration = ChronoUnit.SECONDS.between(cdr.startDate, cdr.endDate);
        } else {
            cdto.duration = -1;
        }

        return cdto;
    }

}
