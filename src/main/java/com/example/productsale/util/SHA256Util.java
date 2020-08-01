package com.example.productsale.util;

import com.example.productsale.exception.SHAException;
import com.example.productsale.msg.Msg;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SHA256Util {

    public static String sign(String value, String key) {
        try {
            Charset charSet = StandardCharsets.US_ASCII;
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(charSet.encode(key).array(), "HmacSHA256");

            sha256HMAC.init(secretKey);
            byte[] macData = sha256HMAC.doFinal(charSet.encode(value).array());
            StringBuilder result = new StringBuilder();
            byte[] var7 = macData;
            int var8 = macData.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                byte element = var7[var9];
                result.append(Integer.toString((element & 255) + 256, 16).substring(1));
            }

            return result.toString();
        } catch (InvalidKeyException e) {
            throw new SHAException.InvalidKey(Msg.getMessage("sha.invalid.key"), e);
        } catch (NoSuchAlgorithmException e) {
            throw new SHAException.InvalidKey(Msg.getMessage("sha.invalid.key"), e);
        }
    }
}
