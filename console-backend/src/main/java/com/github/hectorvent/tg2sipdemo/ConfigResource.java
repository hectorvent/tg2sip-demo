package com.github.hectorvent.tg2sipdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.github.hectorvent.tg2sipdemo.entity.ConfigDto;
import com.github.hectorvent.tg2sipdemo.entity.User;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/config")
@RequestScoped
public class ConfigResource {

    @Inject
    JsonWebToken jwt;

    private ConfigDto createDefaultConfig(){
        ConfigDto cd = new ConfigDto();

        cd.name = "Anonymous";
        cd.phone = "8888888888";

        return cd;
    }

    @GET
    @Produces("application/json")
    public ConfigDto getUser() {

        String username = jwt.getSubject();

        if (username == null){
          return createDefaultConfig();
        }

        User user = User.findByTelegramUsername(username);

        if (user == null){
            return createDefaultConfig();
        }

        return ConfigDto.mappper(user);
    }
}