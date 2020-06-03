package com.github.hectorvent.tg2sipdemo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;


@Path("/auth")
public class AuthResource {

    @ConfigProperty(name = "tg2sipdemo.telegram.bot.username")
    String telegramBotName;

    @POST
    @Path("/check")
    public Response check(TelegramUser telegramUser) {

        StringBuilder dataCheckingString = new StringBuilder();
        dataCheckingString
                .append("auth_date=")
                .append(telegramUser.authDate)
                .append("\n")
                .append("first_name=")
                .append(telegramUser.firstName)
                .append("\n")
                .append("id=")
                .append(telegramUser.id)
                .append("\n")
                .append("last_name=")
                .append(telegramUser.lastName)
                .append("\n")
                .append("phorto_url=")
                .append(telegramUser.photoUrl)
                .append("\n")
                .append("username=")
                .append(telegramUser.username)
                .append("\n");


                String dataCheck = dataCheckingString.toString();


                // TODO: Save user and create JWT-Token

        return Response.ok().build();
    }

    public String sha256(String value){
        MessageDigest digest;
		try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
        }

        return null;

    }


}