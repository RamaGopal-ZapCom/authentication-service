package com.zapcom.service;

import com.zapcom.dto.CustomerResponse;
import com.zapcom.dto.LoginResponse;
import com.zapcom.dto.Response;
import com.zapcom.entity.AuthenticationEntity;
import com.zapcom.exception.InvalidCredentialsException;
import com.zapcom.exception.InvalidRequestException;
import com.zapcom.exception.UserNotFoundException;
import com.zapcom.openfeign.CustomerClient;
import com.zapcom.repository.AuthenticationRepository;
import com.zapcom.utils.JwtUtil;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Created by Rama Gopal Project Name - auth-service */
@Service
@Slf4j
public class AuthService {

  /*
  private final JwtUtil jwtUtil;
  private final AuthenticationRepository authenticationRepository;

  private final EmailService emailService;
  @Value("${email.activation.url}")
  private String activationUrl;


  public AuthService(JwtUtil jwtUtil, AuthenticationRepository authenticationRepository, EmailService emailService) {
      this.jwtUtil = jwtUtil;
      this.authenticationRepository = authenticationRepository;
      this.emailService = emailService;
  }

  public Response generateToken(String email) {
      String activationToken = UUID.randomUUID().toString(); // Generate random activation token
      AuthenticationEntity tokenEntity = new AuthenticationEntity();
      tokenEntity.setEmail(email);
      tokenEntity.setToken(activationToken);
      tokenEntity.setStatus("PENDING");
      authenticationRepository.save(tokenEntity);

      // Send Activation Email
    */
  /*  String activationLink = activationUrl + "?token=" + activationToken;
  String emailBody = "<p>Click the link to activate your account:</p>"
          + "<a href='" + activationLink + "'>" + activationLink + "</a>";*/
  /*

  String activationLink = activationUrl + activationToken;  // Fixed the URL
  String emailBody = "<p>Click the link to activate your account:</p>"
          + "<a href='" + activationLink + "'>" + activationLink + "</a>";

  emailService.sendEmail(email, "Activate Your Account", emailBody);

  */
  /*    String brearToken= jwtUtil.generateToken(email);
  AuthenticationEntity authenticationEntityObject = AuthenticationEntity
          .builder()
          .token(brearToken)
          .email(email)
          .build();
  authenticationRepository.save(authenticationEntityObject);
  //return token;*/
  /*
      Response response = Response
              .builder()
              .message("Registration successful! Activation email sent.")
              .activationLink(activationLink)
              .build();
      return response;

  }


  public String activateToken(String token) {

      Optional<AuthenticationEntity> tokenEntityOptional = authenticationRepository.findByToken(token);
      if (tokenEntityOptional.isEmpty()) {
          return ("Invalid activation token!");
      }
      AuthenticationEntity tokenEntity = tokenEntityOptional.get();

      // Check if the token is already activated
      if ("ACTIVE".equals(tokenEntity.getStatus())) {
          return ("Token already activated!");
      }

      // Activate the token
      tokenEntity.setStatus("ACTIVE");
      authenticationRepository.save(tokenEntity);

      // Generate JWT Token
      String jwtToken = jwtUtil.generateToken(tokenEntity.getEmail());

      // Store JWT token in DB
      tokenEntity.setToken(jwtToken);
      authenticationRepository.save(tokenEntity);

      // Return JWT Token in response
      return jwtToken;
  }*/

  private final JwtUtil jwtUtil;
  private final AuthenticationRepository authenticationRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;
  private final CustomerClient customerClient;

  @Value("${email.activation.url}")
  private String activationUrl;

  public AuthService(
      JwtUtil jwtUtil,
      AuthenticationRepository authenticationRepository,
      EmailService emailService,
      PasswordEncoder passwordEncoder,
      CustomerClient customerClient) {
    this.jwtUtil = jwtUtil;
    this.authenticationRepository = authenticationRepository;
    this.emailService = emailService;
    this.passwordEncoder = passwordEncoder;
    this.customerClient = customerClient;
  }

  public String activateToken(String token) {
    Optional<AuthenticationEntity> tokenEntityOptional =
        authenticationRepository.findByToken(token);
    if (tokenEntityOptional.isEmpty()) {
      return "Invalid activation token!";
    }

    AuthenticationEntity tokenEntity = tokenEntityOptional.get();
    if ("ACTIVE".equals(tokenEntity.getStatus())) {
      return "Token already activated!";
    }

    tokenEntity.setStatus("ACTIVE");
    authenticationRepository.save(tokenEntity);

    // Generate JWT Token
    String jwtToken = jwtUtil.generateToken(tokenEntity.getEmail());
    tokenEntity.setJwtToken(jwtToken);
    tokenEntity.setToken(token);
    tokenEntity.setPassword("rama@123");
    authenticationRepository.save(tokenEntity);

    return jwtToken;
  }

  public Response generateTokenForActivate(String email) {
    String activationToken = UUID.randomUUID().toString(); // Generate random activation token
    AuthenticationEntity tokenEntity = new AuthenticationEntity();
    tokenEntity.setEmail(email);
    tokenEntity.setToken(activationToken);
    tokenEntity.setStatus("PENDING");
    authenticationRepository.save(tokenEntity);

    // Send Activation Email

    String activationLink = activationUrl + activationToken; // Fixed the URL
    String emailBody =
        "<p>Click the link to activate your account:</p>"
            + "<a href='"
            + activationLink
            + "'>"
            + activationLink
            + "</a>";

    emailService.sendEmail(email, "Activate Your Account", emailBody);

    /*      String brearToken= jwtUtil.generateToken(email);
    AuthenticationEntity authenticationEntityObject = AuthenticationEntity
            .builder()
            .token(brearToken)
            .email(email)
            .build();
    authenticationRepository.save(authenticationEntityObject);*/
    // return token;*//*

    Response response =
        Response.builder()
            .message("Registration successful! Activation email sent.")
            .activationLink(activationLink)
            .build();
    return response;
  }

  /* public LoginResponse login(String email, String password) {
          // Find user by email and ACTIVE status
          List<AuthenticationEntity> authEntities = authenticationRepository.findByEmailAndStatus(email, "ACTIVE");

          if (authEntities.size() == 1) {
              AuthenticationEntity authEntity = authEntities.get(0);

              // Validate password
              if (!passwordEncoder.matches(password, authEntity.getPassword())) {
                  throw new RuntimeException("Invalid password");
              }

              // Return response with JWT token and other details
              return new LoginResponse(
                      authEntity.getEmail(),
                      authEntity.getJwtToken(),
                      "Successfully authenticated and sending response to customer"
              );
          } else {
              // Handle case when no user or more than one user is found
              throw new RuntimeException("User not found or not active");
          }
      }
  */

  /*public LoginResponse login(String email, String password) {

    // Fetch customer details from customer-service
    CustomerResponse customer = customerClient.getCustomerByEmail(email).getBody();
    String encodedPassword = customer.password();

    // Validate password
    if (!passwordEncoder.matches(password, encodedPassword)) {
      throw new RuntimeException("Invalid password");
    }

    // Find user by email and ACTIVE status
    List<AuthenticationEntity> authEntities =
        authenticationRepository.findByEmailAndStatus(email, "ACTIVE");
    if (authEntities.size() == 1) {
      AuthenticationEntity authEntity = authEntities.get(0);
      return new LoginResponse(
          authEntity.getEmail(),
          authEntity.getJwtToken(),
          "Successfully authenticated and sending response to customer");
    } else {
      throw new RuntimeException("User not found or not active");
    }
  }*/

  public LoginResponse login(String email, String password) {
    // Step 1: Validate inputs
    if (email == null || email.trim().isEmpty()) {
      throw new InvalidRequestException("Email cannot be empty");
    }
    if (password == null || password.trim().isEmpty()) {
      throw new InvalidRequestException("Password cannot be empty");
    }

    // Step 2: Call Feign client to get customer data
    CustomerResponse customer = customerClient.getCustomerByEmail(email).getBody();
    if (customer == null) {
      throw new UserNotFoundException("No customer found for email: " + email);
    }

    // Step 3: Password validation
    if (!passwordEncoder.matches(password, customer.password())) {
      throw new InvalidCredentialsException("Invalid password");
    }

    // Step 4: Return successful login response
    List<AuthenticationEntity> authEntities =
        authenticationRepository.findByEmailAndStatus(email, "ACTIVE");
    if (authEntities.size() != 1) {
      throw new UserNotFoundException("User not found or not active");
    }

    AuthenticationEntity authEntity = authEntities.get(0);
    return new LoginResponse(
        authEntity.getEmail(),
        authEntity.getJwtToken(),
        "Successfully authenticated and sending response to customer");
  }
}
