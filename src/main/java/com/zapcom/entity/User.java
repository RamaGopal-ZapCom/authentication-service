package com.zapcom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/** Created by Rama Gopal Project Name - auth-service */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  // private Integer id;
  // private String name;
  private String username;

  private String password;
  // private List<String> roles;
}
