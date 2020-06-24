package com.github.hectorvent.tg2sipdemo.utils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

@ApplicationScoped
public class TokenUtils {

	@ConfigProperty(name="mp.jwt.verify.issuer")
	String issuer;

	@ConfigProperty(name="tg2sipdemo.jwt.privatekey.location")
	String privateKeyLocation;

	public String generateToken(String username, Long userId,  Long duration) throws Exception {
        // public static String generateToken(String username, Set<Role> roles, Long duration, String issuer) throws Exception {

		PrivateKey privateKey = readPrivateKey(privateKeyLocation);

		JwtClaimsBuilder claimsBuilder = Jwt.claims();
		long currentTimeInSecs = currentTimeInSecs();

		// Set<String> groups = new HashSet();
		// for (Role role : roles) groups.add(role.toString());

		claimsBuilder.issuer(issuer);
		claimsBuilder.subject(username);
		claimsBuilder.issuedAt(currentTimeInSecs);
		claimsBuilder.expiresAt(currentTimeInSecs + duration);
		// claimsBuilder.groups(groups);

		claimsBuilder.claim("telegramId", String.valueOf(userId));

		return claimsBuilder.jws().signatureKeyId(privateKeyLocation).sign(privateKey);
	}

	public static final Optional<Long> getUserId(JsonWebToken jwt){
		Optional<String> userId = jwt.claim("telegramId");

		Long id = null;
		try {
			id = Long.valueOf(userId.get());
		} catch(Exception ex){
		}

		return Optional.ofNullable(id);
	}

	private PrivateKey readPrivateKey(final String pemResName) throws Exception {
		try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
			byte[] tmp = new byte[4096];
			int length = contentIS.read(tmp);
			return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
		}
	}

	private PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
		byte[] encodedBytes = toEncodedBytes(pemEncoded);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(keySpec);
	}

   public  byte[] toEncodedBytes(final String pemEncoded) {
		final String normalizedPem = removeBeginEnd(pemEncoded);
		return Base64.getDecoder().decode(normalizedPem);
	}

	private String removeBeginEnd(String pem) {
		pem = pem.replaceAll("-----BEGIN (.*)-----", "");
		pem = pem.replaceAll("-----END (.*)----", "");
		pem = pem.replaceAll("\r\n", "");
		pem = pem.replaceAll("\n", "");
		return pem.trim();
	}

	private int currentTimeInSecs() {
		long currentTimeMS = System.currentTimeMillis();
		return (int) (currentTimeMS / 1000);
	}

}
