
package com.rentme.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

@Configuration
public class BraintreeConfig {

  @Value("${braintree.environment}")
  private String environment;

  @Value("${braintree.merchantId}")
  private String merchantId;

  @Value("${braintree.publicKey}")
  private String publicKey;

  @Value("${braintree.privateKey}")
  private String privateKey;

  @Bean
  public BraintreeGateway braintreeGateway() {
    return new BraintreeGateway(getBraintreeEnvironment(environment), merchantId, publicKey,
        privateKey);
  }

  private Environment getBraintreeEnvironment(String env) {
    if ("production".equalsIgnoreCase(env)) {
      return Environment.PRODUCTION;
    } else {
      return Environment.SANDBOX;
    }
  }
}

