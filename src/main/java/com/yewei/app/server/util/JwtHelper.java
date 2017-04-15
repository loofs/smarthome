package com.yewei.app.server.util;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtHelper {

    // HS256 加密密钥
    private static final byte[] SECRET_KEY = JwtHelper.class.getCanonicalName().getBytes();

    private static final String ISSUER = "localhost";

    public static Claims parseJWT(String jsonWebToken) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jsonWebToken).getBody();
    }

    public static String createJWT(String audience, long expires, Map<String, Object> claimData) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        Key signingKey = new SecretKeySpec(SECRET_KEY, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setIssuer(ISSUER)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);

        if (claimData != null) {
            claimData.forEach((key, value) -> builder.claim(key, value));
        }
        builder.setExpiration(new Date(expires));
        //生成JWT
        return builder.compact();
    }
}