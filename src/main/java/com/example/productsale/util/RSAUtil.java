package com.example.productsale.util;

import com.example.productsale.exception.RSAException;
import com.example.productsale.msg.Msg;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    private static final String RSA = "RSA";

    public static PublicKey getPublicKey(String content){
        try{
            byte[] byteKey = Base64.getDecoder().decode(content);
            X509EncodedKeySpec x509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(x509publicKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        } catch (InvalidKeySpecException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        }
    }

    public static PrivateKey getPrivateKey(String content) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(content.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RSAException.InvalidPrivateKey(Msg.getMessage("rsa.invalid.private.key"), e);
        } catch (InvalidKeySpecException e) {
            throw new RSAException.InvalidPrivateKey(Msg.getMessage("rsa.invalid.private.key"), e);
        }
    }

    public static String encrypt(String data, PublicKey publicKey) {
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptOut = c.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptOut);
        } catch (NoSuchAlgorithmException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        } catch (NoSuchPaddingException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        } catch (BadPaddingException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        } catch (IllegalBlockSizeException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        } catch (InvalidKeyException e) {
            throw new RSAException.InvalidPublicKey(Msg.getMessage("rsa.invalid.public.key"), e);
        }
    }

    public static String encrypt(String data, String publicKey) {
        return encrypt(data, getPublicKey(publicKey));
    }
}
