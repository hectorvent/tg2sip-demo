package com.github.hectorvent.tg2sipdemo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.github.hectorvent.tg2sipdemo.entity.User;

import java.util.List;

@Path("/user")
public class UserResource {

    @GET
    @Produces("application/json")
    public List<User> getUsers() {
        return User.findAll().list();
    }
}