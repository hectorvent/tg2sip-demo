package com.github.hectorvent.tg2sipdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.hectorvent.tg2sipdemo.telegram.TelegramAuthValidator;
import com.github.hectorvent.tg2sipdemo.entity.User;
import com.github.hectorvent.tg2sipdemo.telegram.TelegramAuthData;
import com.github.hectorvent.tg2sipdemo.utils.TokenUtils;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@Path("/auth")
@RequestScoped
public class AuthResource {

    @Inject
    TokenUtils tokenUtils;

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.username")
    String telegramBotName;

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.token")
    String telegramBotToken;

    @POST
    @Path("/check")
    @Transactional
    public Response check(TelegramAuthData authData) {

       boolean isValid =  TelegramAuthValidator.createIntance()
            .setAuthData(authData)
            .setBotToken(telegramBotToken)
            .validate();

        if (!isValid){
            return Response.status(Status.BAD_REQUEST).build();
        }

        try {
            String token = tokenUtils.generateToken(authData.username, 3600L);

            User user = User.findByTelegramId(authData.id);

            if (user == null){
                user = new User();
                user.telegramId = authData.id;
                user.telegramName = authData.getFullName();
                user.telegramUsername = authData.username;

                user.persist();
            }

            return Response.ok(token).build();
        } catch(Exception ex){
        }

        return Response.status(Status.BAD_REQUEST).build();
    }
}