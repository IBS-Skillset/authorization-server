package com.example.authserver.config.keys;

import com.nimbusds.jose.jwk.RSAKey;

import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

public class JwksKeys {

  public static RSAKey generateRSAKey() {
    try {
      KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
      g.initialize(2048);
      var keyPair = g.generateKeyPair();

      RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
      RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

      return new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).keyID(UUID.randomUUID().toString()).build();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Problem generating the keys");
    }


//    public static RSAKey generateRSAKey1 ()  throws Exception {
//      try {
//        KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
//        g.initialize(2048);
//        var keyPair = g.generateKeyPair();
//
//        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
//
//        RSAKey ks = new RSAKey.Builder("sdfd").
//
//       // RSAKey ks = new PKCS8EncodedKeySpec("MIIEpAIBAAKCAQEAyZFpm2uUnA8j05IdQkuubvNuVQK4vxTQiDsNygf7Tnhvk+a3mBrKJpfbEVJRXhkwnoOXqGCp1DyjI2PcKN5LAzuzmgUSZI21sHMScCfgU0AMdM/8HeSXK60Ct0bG5XR7XOnhuwNx7MbXvqkIDIYCr7yBx1kDxuHtjFY49wlEdGqZhKv4UxK9zSt5J68nWLWQVZy4BoW77+BykyoTUl9Mgm2otHl1F6HFQfc/VXdoye6MVrG927CjHA8QkcEf+I0oVbnAQ//k/5ZxBB8teIeLcstJbCpJQVRF2KwJ/lCUwlq7O7KLTdfQewGNH68u0vM9oX8AqOao/lksZQ3vGJJQOwIDAQABAoIBAHw+UWYnbbaz7lVe3wIaPC7PhdaIku/VGKSgIePB/7liqwPzVn8/JBOdzr5iix0BUeKjIjv7OTNFHRmXWAEo4/veNNpe/fNVFgTpKRhQvAkUlZj7CmAyoqudPOtTiQ2uOswOrcDkYrib6CfxcLCnechw3V3PFHDmmdHC4+IBqFxIrt6jX8ZCCPYtHHB6MH2eguelc/oh7jv/R0Q/GYUIpX+4pyI+gPWY+BhfOvrPxJXxeaqXqgMjhB32fVKW9jI7sV6I3ZZZyY6rRrAl6tpoWY4hWaWcmsHNnPN3ksGgZn6FASuIao73CZEqNAgV2UqI4fU75NC8uZzJNmha59k+YWECgYEA/zvT94veXgc4QBqIoVI4fDChlTcTb82vqa1Zpb9ARIIBz4JPuc4UCEyA+U01R5pwpktGEj7wZ1QfXBKgnCKroGWzDNFVUcqHgZRYDO/faO/tnqE4qnprHHrnSIq3k46GQ28wsUBFGPoMOHzD+kroqd05Sfnl09LflbtMoVgrU80CgYEAyixWU8+vX2REX7lUcyq3/I5hS4YGB4BgxnEeFhgjp3HetlKZcyssXJk/F8HfOLLWZQR96veXmo08nlwrO47KUFVBj4iFafYjAM3rnFYkJH+AW9ZJMubMK5CXEAanH5PUeE1sI8WfVlbuWh9lYP2/nfGPMAAIzYE8++wvxggmvCcCgYEAwwlU0LVxFyktMs8T2TKbN0tyLjQIBkeHFIIDH0UmNzOhbpCFm9Z+OFk9tUPqkuzRaEHdKdQuoMcHr7QMR0O3IrqUOBc+u2GoaryRDFHAxs+RJMbOJnelof3I/wYBgiBHOQCelNQn+Q4aiI/OslTLtgH25TCxD0z0zdZymFhnw4ECgYBLXD2NnjPj3e8ys1WMXFcauEQCL+Idd7gbIF0zpSndJ6/5zE5QxL4TgjgndefsLZWHMCZzqHkFbjIzYW3oFHDLgwweIaiBd/NSQG/b5awxQqmpEfZVF/zxHEODAMfZGHSxp0JxDyA2wc61H1Dd15hOmpViMhEV8V+3FDI2oiE2LwKBgQDmh30GQdYdV8RJn2p5erW6V1sgEzNL6jUfNvOjPiun31J+Ji/SXxvAeBjSj7383sKj3UTXbcnFmchNZUjQix2K7Lp7ZQVnqQSqiG4F8x/aj8g5azu5Aalh/1TrXl1vfQ+JZWzCr2yO6wrl1TPq88EkaQQsoP0MaoULqp95lWtEGA==".getBytes(StandardCharsets.UTF_8));
//
////        KeyPairGenerator kf = KeyPairGenerator.getInstance("RSA");
////        RSAPrivateKey privateKey = (RSAPrivateKey) kf.generateKeyPair(ks);
////        // RSAPrivateKey
//
//        new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).keyID(UUID.randomUUID().toString()).build();
//        String publicKeyB64 = "MIGHAoGBAOX+TFdFVIKYyCVxWlnbGYbmgkkmHmEv2qStZzAFt6NVqKPLK989Ow0RcqcDTZaZBfO5"
//                + "5JSVHNIKoqULELruACfqtGoATfgwBp4Owfww8M891gKNSlI/M0yzDQHns5CKwPE01jD6qGZ8/2IZ"
//                + "OjLJNH6qC9At8iMCbPe9GeXIPFWRAgER";
//
//
//        return new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).keyID(UUID.randomUUID().toString()).build();
//      } catch (NoSuchAlgorithmException e) {
//        throw new RuntimeException("Problem generating the keys");
//      }
  //  }
  }
}
