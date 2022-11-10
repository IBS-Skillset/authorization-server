package com.mystays.authorizationserver.config.keys;

import com.mystays.authorizationserver.config.RSAConfiguration;
import com.mystays.authorizationserver.constants.AuthConstants;
import com.nimbusds.jose.jwk.RSAKey;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class JwksKeys {
    public static RSAKey generateRSAKey(RSAConfiguration rsaConfiguration) throws Exception {
        try {
            BigInteger mod = new BigInteger(rsaConfiguration.getMod());
            BigInteger publicExp = new BigInteger(rsaConfiguration.getPublicExp());
            BigInteger privateExp = new BigInteger(rsaConfiguration.getPrivateExp());
            KeyFactory fact = KeyFactory.getInstance("RSA");
            String uuidRSA = AuthConstants.RSA_UUID;
            PublicKey publicKey = fact.generatePublic(new RSAPublicKeySpec(mod, publicExp));
            PrivateKey privateKey = fact.generatePrivate(new RSAPrivateKeySpec(mod, privateExp));
            RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
            return new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).keyID(uuidRSA).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Problem generating the keys");
        }
    }
}
