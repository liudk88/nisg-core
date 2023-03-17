package com.hcxinan.core.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 对传入的敏感参数，前端默认使用加密的处理
 */
public class AESUtil {

    /**
     * 系统使用 AES 算法 对 字符串进行加密
     */
    public static final String ALGORITHM = "AES";
    /**
     * 系统使用加密密钥
     */
    public static final String PRIVATEKEY = "UPATHS_KEY";

    /**
     * 返回系统默认的私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(PRIVATEKEY.getBytes());
        keyGen.init(128, random);
        SecretKey key = keyGen.generateKey();
        return key;
    }

    /**
     * 对传入的字符串使用系统默认的算法进行加密
     *
     * @param source
     * @return
     * @throws PlatformException
     */
    public static String encrypt(String source) throws Exception {
        byte[] data = source.getBytes(StandardCharsets.UTF_8);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey());
        data = cipher.doFinal(data);
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 对传入的字符串使用系统的解密方法来解密，解密字符串必须是以前加过密的字符
     *
     * @param source
     * @return
     * @throws PlatformException
     */
    public static String decrypt(String source) throws Exception {
        byte[] data = Base64.getDecoder().decode(source.getBytes());
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, generateSecretKey());
        data = cipher.doFinal(data);
        return new String(data, StandardCharsets.UTF_8);
    }
}