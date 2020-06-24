package com.github.hectorvent.tg2sipdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
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

        String token = null;
        try {
            token = tokenUtils.generateToken(authData.username, authData.id, 3600L);
        } catch(Exception ex){
            return Response.status(Status.BAD_REQUEST).build();
        }

        User user = User.findByTelegramId(authData.id);

        if (user == null){
            user = new User();
            user.telegramId = authData.id;
            user.telegramName = authData.getFullName();
            user.telegramUsername = authData.username;
            user.telegramPhoto = authData.photoUrl;

            user.persist();
        } else {
            user.telegramPhoto = authData.photoUrl;
            user.flush();
        }

        return Response.ok(Json.createObjectBuilder().add("token", token).build()).build();
    }
}
