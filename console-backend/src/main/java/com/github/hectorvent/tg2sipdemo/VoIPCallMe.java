package com.github.hectorvent.tg2sipdemo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.github.hectorvent.tg2sipdemo.entity.User;
import com.github.hectorvent.tg2sipdemo.utils.TokenUtils;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/callme")
@RequestScoped
public class VoIPCallMe {

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces("application/json")
    public Response callMe() {

        Response response = Response.ok().build();
        TokenUtils.getUserId(jwt)
            .flatMap(User::findByTelegramId)
            .ifPresentOrElse(u-> {
                // TODO: here execute ESL Freeswitch to call this user
            }, () -> {

            });

        return response;
    }

}
