package com.example.productsale.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityProperties {

    private JwtProperties jwt;
    private String extraAuthWhiteList;
    private String resourceId = "isofh";
}
