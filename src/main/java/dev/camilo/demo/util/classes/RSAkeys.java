package dev.camilo.demo.util.classes;

import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class RSAkeys {

  /**
   * Metodo para generar un keyPair con RSA
   * @return KeyPair
   */
  public static KeyPair generateRSA(){
    KeyPair keyPair = null;
    try {
      var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
      keyPair = keyPairGenerator.generateKeyPair();
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    return keyPair;
  }

  /**
   * Metodo para generar las llaves (publica y privada)
   * @return RSAKey
   */
  public static RSAKey generateKeys(){
    var keyPair = generateRSA();
    var publicKey = (RSAPublicKey) keyPair.getPublic();
    var privateKey = (RSAPrivateKey) keyPair.getPrivate();
    return new RSAKey
        .Builder(publicKey)
        .privateKey(privateKey)
        .keyID(UUID.randomUUID().toString())
        .build();
  }
}
