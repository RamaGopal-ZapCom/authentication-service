package com.zapcom.controller;

import com.zapcom.dto.JwtResponse;
import com.zapcom.dto.LoginRequest;
import com.zapcom.dto.LoginResponse;
import com.zapcom.dto.Response;
import com.zapcom.service.AuthService;
import com.zapcom.utils.JwtUtil;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Created by Rama Gopal Project Name - auth-service */
@RestController
@RequestMapping("${application.path}")
public class AuthController {
  private final AuthService authService;

  private final JwtUtil jwtUtil;

  public AuthController(AuthService authService, JwtUtil jwtUtil) {
    this.authService = authService;

    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/generateToken/{email}")
  public ResponseEntity<?> generateToken(@PathVariable String email) {
    Response response = authService.generateTokenForActivate(email);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/activate/{token}")
  public ResponseEntity<?> activateToken(@PathVariable String token) {
    String jwtToken = authService.activateToken(token);
    JwtResponse jwtResponse = new JwtResponse(jwtToken);
    return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
  }

  @PostMapping("/login")
  @Validated
  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
    LoginResponse response = authService.login(request.email(), request.password());
    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
  }

  @PostMapping("/validate-token")
  public ResponseEntity<String> validateToken(
      @RequestHeader("Authorization") String token, @RequestBody Map<String, String> requestBody) {

    String businessEmail = requestBody.get("businessEmail");

    // Remove 'Bearer ' prefix if present
    if (token.startsWith("Bearer ")) {
      token = token.substring(7);
    }

    boolean isValid = jwtUtil.validateToken(token, businessEmail);
    if (isValid) {
      return ResponseEntity.ok("Token is valid and belongs to the given email.");
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body("Invalid token or email mismatch.");
    }
  }
}
