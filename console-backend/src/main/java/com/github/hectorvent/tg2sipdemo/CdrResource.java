package com.github.hectorvent.tg2sipdemo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.github.hectorvent.tg2sipdemo.entity.Cdr;
import com.github.hectorvent.tg2sipdemo.entity.CdrDto;
import com.github.hectorvent.tg2sipdemo.utils.TokenUtils;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/cdr")
@RequestScoped
public class CdrResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @PermitAll
    @Produces("application/json")
    public List<CdrDto> getLastCdr(@QueryParam("mine") boolean mine) {

        Optional<Long> telegramId = TokenUtils.getUserId(jwt);

        List<Cdr> calls = Cdr.findAll().list();
            return calls.stream()
                .map( c -> CdrDto.mappper(c, telegramId))
                .collect(Collectors.toList());
    }

}
