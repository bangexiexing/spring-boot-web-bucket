package bucket.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 13:12 2018/7/8
 */
public class SignatureUtil {

    private static final String RSA = "RSA";

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static String getPublicKeyString(KeyPair keyPair){
        return Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    }

    public static String getPrivateKeyString(KeyPair keyPair){
        return Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
    }

    public static KeyPair generateRsaKeyPair(int keySize)
            throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = null;
        keyPairGen = KeyPairGenerator.getInstance(RSA);
        keyPairGen.initialize(keySize, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        return keyPair;
    }

    public static PublicKey getRsaX509PublicKey(String key)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(key)));
    }

    public static PublicKey getRsaX509PublicKey(byte[] encodedKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey getRsaPkcs8PrivateKey(String key)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
    }

    public static PrivateKey getRsaPkcs8PrivateKey(byte[] encodedKey)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }

    public static byte[] sign(SignatureAlgorithm algorithm, PrivateKey privateKey, String data)
            throws GeneralSecurityException {
        return sign(algorithm, privateKey, data, DEFAULT_CHARSET);
    }

    public static byte[] sign(SignatureAlgorithm algorithm, PrivateKey privateKey,
                              String data, Charset charset)
            throws GeneralSecurityException {
        return sign(algorithm, privateKey, data.getBytes(charset));
    }

    public static byte[] sign(SignatureAlgorithm algorithm, PrivateKey privateKey,
                              byte[] data) throws GeneralSecurityException {
        Signature signature = Signature.getInstance(algorithm.name());
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    public static boolean verify(SignatureAlgorithm algorithm, PublicKey publicKey,
                                 String data, byte[] sign)
            throws GeneralSecurityException {
        return verify(algorithm, publicKey, data, DEFAULT_CHARSET, sign);
    }

    public static boolean verify(SignatureAlgorithm algorithm, PublicKey publicKey,
                                 String data, Charset charset, byte[] sign)
            throws GeneralSecurityException {
        return verify(algorithm, publicKey, data.getBytes(charset), sign);
    }

    public static boolean verify(SignatureAlgorithm algorithm, PublicKey publicKey, byte[] data, byte[] sign)
            throws GeneralSecurityException {
        Signature signature = Signature.getInstance(algorithm.name());
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
}
