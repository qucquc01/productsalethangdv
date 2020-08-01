package com.example.productsale.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtProperties {

    private String privateKeyStore = "keystore/productsale.jks";
    private String publicKeyStore = "keystore/public.txt";
    private String keyStorePassword = "123456";
    private String keyPairAlias = "productsale";
    private String keyPairPassword = "123456";
    private Long expirationInMinutes = 1000L;
}
