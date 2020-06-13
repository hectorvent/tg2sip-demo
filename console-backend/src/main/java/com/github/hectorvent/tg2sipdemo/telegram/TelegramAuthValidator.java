package com.github.hectorvent.tg2sipdemo.telegram;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TelegramAuthValidator {

    private TelegramAuthData authData;
    private String botToken;

    public static final TelegramAuthValidator createIntance(){
        return new TelegramAuthValidator();
    }

    public TelegramAuthValidator setBotToken(String botToken){
        this.botToken = botToken;
        return this;
    }

    public TelegramAuthValidator setAuthData(TelegramAuthData authData){
        this.authData = authData;
        return this;
    }

    public boolean validate(){

        StringBuilder dataCheckString = new StringBuilder("")
                .append("auth_date=")
                .append(authData.authDate)
                .append("\n")
                .append("first_name=")
                .append(authData.firstName)
                .append("\n")
                .append("id=")
                .append(authData.id)
                .append("\n")
                .append("last_name=")
                .append(authData.lastName)
                .append("\n")
                .append("photo_url=")
                .append(authData.photoUrl)
                .append("\n")
                .append("username=")
                .append(authData.username);

                String value = dataCheckString.toString();

                byte[] secretKey = getSecretKey(botToken);
                String resultHash = getHmacSha256(secretKey, value.getBytes());

        return resultHash.equals(authData.hash);
    }

    private byte[] getSecretKey(String value){
        MessageDigest digest;
		try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return hash;
		} catch (NoSuchAlgorithmException e) {
        }

        return null;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private String getHmacSha256(byte[] secretKey, byte[] message) {
        byte[] hmacSha256 = null;
        try {
          SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
          Mac mac = Mac.getInstance("HmacSHA256");
          mac.init(secretKeySpec);
          hmacSha256 = mac.doFinal(message);
        } catch (Exception e) {
          throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return bytesToHex(hmacSha256);
      }

}