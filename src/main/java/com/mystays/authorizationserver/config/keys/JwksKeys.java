package com.mystays.authorizationserver.config.keys;

import com.nimbusds.jose.jwk.RSAKey;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.UUID;

public class JwksKeys {

  public static RSAKey generateRSAKey() throws Exception {
    try {
      BigInteger mod = new BigInteger("19000812822426683865012163618027310378487395154330304998855217229900382730200283232802318000193794545462996454857896773995909120073180895021730312708474638199242368948617010756177040604721432026065868235078315166342546553887368687889480198247689830862136303023640201198180314337176952541013193315987840899973486485532199280808520596592328947878088072286427483154626278976886368730181756526507275308753625752548958224081969926946453900525575829301891927935306879737770979639402074921073866173771711955654133667856118067012688187894392311708798013285013475533536647677724106088022279193658129435945930408108920563375353");
      BigInteger publicExp = new BigInteger("65537");
      BigInteger privateExp = new BigInteger("16398446283004648764651311265380513333195379057998937715187603059956902934263976378694964804750922582448579313719234927353748504515909861655615731832461571496823895358388799386302845623749163324935709190903834232929502046697643474002729595390200719034946540661307557867620989047790799283950245314030063462523219542995791852487044112577967532667057656348739016140269374627936398785932565048054785514640051175183115398499187856707562737735866544603817087886236143903531714734470765552999085016053780190550082802469897951901723741965051834064822987173518232970379924523160503097650745483026631358738384324672079120795137");
      KeyFactory fact = KeyFactory.getInstance("RSA");
      String uuidRSA = "421903e4-0efd-46ed-b3de-ba27f713457f";

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
