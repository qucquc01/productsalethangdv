package com.example.productsale.security;

import com.example.productsale.storage.StorageService;
import com.example.productsale.util.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenProvider {

    private final String JWT_SECRET = "examplecode1aaaaaaaa";

    @Autowired
    StorageService storageService;

    @Autowired
    SecurityProperties securityProperties;

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

    public Map<String, Object> generateToken(Long id, String username, String email, Map<String, Object> addInformation){
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
//        JwtBuilder jwtBuilder = Jwts.builder().claim("id", id).claim("username", username).claim("email", email);
//        String jwt = jwtBuilder.setSubject(Long.toString(id)).setIssuedAt(now).setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Date expiryDate = DateUtil.getDate(zonedDateTime.plusMinutes(securityProperties.getJwt().getExpirationInMinutes()));
        JwtProperties jwtProperties = securityProperties.getJwt();
        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

        JwtBuilder jwtBuilder = Jwts.builder().setSubject(Long.toString(id))
                .claim("user_name", username)
                .claim("email", email);

        if (addInformation != null) {
            for (String key: addInformation.keySet()) {
                jwtBuilder.claim(key, addInformation.get(key));
            }
        } else {
            addInformation = new HashMap<>();
        }

        String jwt = jwtBuilder.setIssuedAt(DateUtil.getDate(zonedDateTime)).setExpiration(expiryDate).signWith(SignatureAlgorithm.RS256, keyPair.getPrivate()).compact();

        addInformation.put("access_token", jwt);
        addInformation.put("expiration", expiryDate);
        addInformation.put("id", id);
        addInformation.put("username", username);
        addInformation.put("email", email);
        return addInformation;
    }

    public Long getUserIdFromToken(String token){
        JwtProperties jwtProperties = securityProperties.getJwt();
        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));
        Claims claims = Jwts.parser().setSigningKey(keyPair.getPrivate()).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(JwtProperties jwtProperties){
        return new KeyStoreKeyFactory(storageService.loadAsResource(jwtProperties.getPrivateKeyStore(), null), jwtProperties.getKeyStorePassword().toCharArray());
    }

    private KeyPair keyPair(JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory){
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }
}
