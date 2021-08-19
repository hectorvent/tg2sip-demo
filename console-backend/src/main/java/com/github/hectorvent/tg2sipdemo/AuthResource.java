package com.github.hectorvent.tg2sipdemo;

import java.util.Optional;

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

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Path("/auth")
@RequestScoped
public class AuthResource {

    private static final Logger LOG = Logger.getLogger(AuthResource.class);

    @Inject
    TokenUtils tokenUtils;

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.token")
    String telegramBotToken;

    @POST
    @Path("/check")
    @Transactional
    public Response check(TelegramAuthData authData) {

       boolean isValid = TelegramAuthValidator.createInstance()
            .setAuthData(authData)
            .setBotToken(telegramBotToken)
            .validate();

        if (!isValid){
            LOG.error("Error validating Telegram received auth data");
            return Response.status(Status.BAD_REQUEST).build();
        }

        String token = null;
        try {
            token = tokenUtils.generateToken(authData.username, authData.id, 3600L);
        } catch(Exception ex) {
            LOG.error("Error generating token: ", ex);
            return Response.status(Status.BAD_REQUEST).build();
        }

        Optional<User> optUser = User.findByTelegramId(authData.id);

        if (optUser.isPresent()) {
            User user = optUser.get();
            user.telegramPhoto = authData.photoUrl;
            user.flush();
        } else {
            User user = new User();
            user.telegramId = authData.id;
            user.telegramName = authData.getFullName();
            user.telegramUsername = authData.username;
            user.telegramPhoto = authData.photoUrl;
            user.persist();
        }

        return Response.ok(Json.createObjectBuilder().add("token", token).build()).build();
    }
}
