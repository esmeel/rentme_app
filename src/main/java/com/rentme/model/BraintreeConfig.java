
package com.rentme.model;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;


@Configuration
public class BraintreeConfig {

 @Bean
 public BraintreeGateway gateway() {
  return new BraintreeGateway(Environment.SANDBOX, "zfrj8tp93kfpxxkf", "y25q9bmtr8mnk3g8",
    "0b296c15e4d6268935d88beaf789c101");
 }

}

