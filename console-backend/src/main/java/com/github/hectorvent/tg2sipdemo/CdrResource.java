package com.github.hectorvent.tg2sipdemo;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.github.hectorvent.tg2sipdemo.entity.Cdr;


@Path("/cdr")
public class CdrResource {

    @GET
    @Produces("application/json")
    public List<Cdr> getLast() {
        return Cdr.findAll().list();
    }
}