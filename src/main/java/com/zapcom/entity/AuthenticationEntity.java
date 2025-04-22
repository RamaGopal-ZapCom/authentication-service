package com.zapcom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Created by Rama Gopal Project Name - auth-service */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "auth_tab")
public class AuthenticationEntity {

  @Id private String id;
  private String token;
  private String jwtToken;
  private String email;
  private String password;
  private String status; // PENDING or ACTIVE
}
