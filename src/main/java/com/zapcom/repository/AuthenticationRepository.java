package com.zapcom.repository;

import com.zapcom.entity.AuthenticationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/** Created by Rama Gopal Project Name - auth-service */
public interface AuthenticationRepository extends MongoRepository<AuthenticationEntity, String> {
  Optional<AuthenticationEntity> findByToken(String token);

  List<AuthenticationEntity> findByEmailAndStatus(String email, String status);
}
