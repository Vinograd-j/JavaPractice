package net.vinograd.newlookatjava.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.vinograd.newlookatjava.api.security.dtos.UserDetails;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    KeyPair pair = Jwts.SIG.RS256.keyPair().build();

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails user){
        return generateToken(new HashMap<>(), user);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails user){
        return Jwts
                .builder()
                .subject(user.getLogin())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 500 * 60))
                .signWith(pair.getPrivate(), Jwts.SIG.RS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(pair.getPublic())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    public boolean isTokenValid(String token, UserDetails user){
        return (extractUsername(token).equals(user.getLogin())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}