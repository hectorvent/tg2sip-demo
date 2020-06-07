package com.github.hectorvent.tg2sipdemo;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.github.hectorvent.tg2sipdemo.telegram.LoginValidate;
import com.github.hectorvent.tg2sipdemo.telegram.TelegramAuthData;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@Path("/auth")
public class AuthResource {

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.username")
    String telegramBotName;

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.token")
    String telegramBotToken;

    @POST
    @Path("/check")
    public Response check(TelegramAuthData authData) {

       boolean isValid =  LoginValidate.createIntance()
            .setAuthData(authData)
            .setBotToken(telegramBotToken)
            .validate();

        System.out.println("Is valid : "+isValid);

        return Response.ok().build();
    }
}