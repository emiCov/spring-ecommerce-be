package org.emi.spring_ecommerce_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringEcommerceBeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringEcommerceBeApplication.class, args);
  }
}
