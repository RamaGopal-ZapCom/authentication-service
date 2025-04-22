package com.zapcom.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Created by Rama Gopal Project Name - auth-service */
@Service
public class JwtUtil {

  @Value("${app.secret}")
  private String secret;

  public String generateToken(String email) {
    try {
      // Encode the key properly
      Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

      return Jwts.builder()
          .setSubject(email)
          .setIssuedAt(new Date())
          .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 1 day expiry
          .signWith(key, SignatureAlgorithm.HS256)
          .compact();
    } catch (Exception e) {
      throw new RuntimeException("Error while generating token", e);
    }
  }

  // validate the token through the API GATE WAY(GATE-WAY Service)
  public String validateToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secret)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  // Method to extract claims from JWT token
  public Claims extractClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secret.getBytes()) // Decode secret key
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  // Validate token and check if email matches
  public boolean validateToken(String token, String businessEmail) {
    try {
      Claims claims = extractClaims(token);
      String emailFromToken = claims.getSubject(); // Extract subject (email)
      return emailFromToken.equals(businessEmail); // Compare with provided email
    } catch (Exception e) {
      e.printStackTrace();
      return false; // Token is invalid or email does not match
    }
  }
}
