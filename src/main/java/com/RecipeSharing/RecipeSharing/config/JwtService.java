package com.RecipeSharing.RecipeSharing.config;


import com.RecipeSharing.RecipeSharing.user.User;
import com.RecipeSharing.RecipeSharing.user.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final UserRepository userRepository;

    private static final String SECRET_KEY = "404E635266556A586e3272357538782F4134428472B4B6250645367566B5970";

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public int getUserId(String token) {

        String username = this.extractUsername(token);

        // Check if the username is not null (or handle accordingly)
        if (username != null) {
            // Use the username to find the user in the repository
            Optional<User> userOptional = userRepository.findByEmail(username);

            // If the user is found, return the user's ID
            if (userOptional.isPresent()) {
                return userOptional.get().getId();
            }
        }

        // If the username is null or the user is not found, return a suitable value (e.g., -1)
        return -1;
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000 *60 *24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token,UserDetails user){
        final String username = extractUsername(token);
        return (username.equals(user.getUsername()))&& !isTokenExpired(token) ;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
