package com.zapcom.openfeign;

import com.zapcom.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/** Created by Rama Gopal Project Name - client-service */
@FeignClient(name = "client-service", url = "${client-service.base-url}")
public interface CustomerClient {

  @GetMapping("/{email}")
  ResponseEntity<CustomerResponse> getCustomerByEmail(@PathVariable("email") String email);
}
