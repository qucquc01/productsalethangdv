package com.example.productsale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.example"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProductsaleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsaleApplication.class, args);
    }

}
