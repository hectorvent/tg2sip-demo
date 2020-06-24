package com.github.hectorvent.tg2sipdemo;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.github.hectorvent.tg2sipdemo.entity.ConfigDto;
import com.github.hectorvent.tg2sipdemo.entity.User;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/config")
@RequestScoped
public class ConfigResource {

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.username")
    String telegramBotName;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces("application/json")
    public ConfigDto getUser() {

    ConfigDto config = ConfigDto.create(telegramBotName);
        Optional<Long> telegramId = jwt.claim("telegramId");

        telegramId.ifPresent(id ->{
            User user = User.findByTelegramId(id);
            config.setUserProperties(user);
        });

        return config;
    }
}
