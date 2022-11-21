package com.niit.authentication.service;

import com.niit.authentication.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtSecurityTokenGenerator implements SecurityTokenGenerator{
    @Override
    public Map<String,String> generateToken(User user){
        String jsonWebToken=null;

        JwtBuilder jwtBuilder= Jwts.builder();
        jsonWebToken=jwtBuilder.setSubject(user.getUserName()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256,"secret").compact();

        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",jsonWebToken);
        tokenMap.put("message","User Successfully Logged In");

        return tokenMap;
    }
}
