package com.assist.sm4;

import com.assist.sm4.SystemUtil;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;


/**
 * SM4算法：SM4分组密码算法是我国自主设计的分组对称密码算法，用于实现数据的加密/解密运算，以保证数据和信息的机密性。要保
 * 证一个对称密码算法的安全性的基本条件是其具备足够的密钥长度，SM4算法与AES算法具有相同的密钥长度分组长度128比特，因此在
 * 安全性上高于3DES算法。
 */
public class SM4Util {

    public static final String ALGORITHM_NAME = "SM4";
    public static final String keyRec = "A7E74D2B6282AEB1C5EA3C28D25660A7";
	public static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 加密算法/分组加密模式/分组填充方式
     * PKCS5Padding-以8个字节为一组进行分组加密
     * 定义分组加密模式使用：PKCS5Padding
     * 28-32位16进制；256-64位16进制
     */
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    public static final int DEFAULT_KEY_SIZE = 128;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }


    /**
     * 自动生成密钥
     */
    @SneakyThrows
    public static String generateKey()  throws Exception{
        return new String(SystemUtil.encodeHex(generateKey(DEFAULT_KEY_SIZE),false));
    }


    /**
     * 指定长度生成密匙
     */
    @SneakyThrows
    public static byte[] generateKey(int keySize)  throws Exception{
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * 生成ECB暗号
     * ECB模式（电子密码本模式：Electronic codeBook）
     *
     * @param algorithmName 算法名称
     * @param mode          模式
     * @param key           byte[]
     * @return Cipher
     * @throws Exception 异常
     */
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }


    /**
     * sm4加密
     * 加密模式：ECB 密文长度不固定，会随着被加密字符串长度的变化而变化
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception 异常
     */
    @SneakyThrows
    public static String encryptEcb(String hexKey, String paramStr) throws Exception{
        String cipherText;
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[]
        byte[] srcData = paramStr.getBytes(CHARSET_UTF_8);
        // 加密后的数组
        byte[] cipherArray = encryptEcb(srcData, keyData);
        // byte[]-->hexString
        cipherText = ByteUtils.toHexString(cipherArray);
        return cipherText;
    }

    /**
     * 加密模式之Ecb
     *
     * @param data byte[] 加密数据
     * @param key  byte[] 密钥key
     * @return byte[]
     * @throws Exception 异常
     */
    private static byte[] encryptEcb(byte[] data, byte[] key) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * sm4解密
     * 解密模式：采用ECB
     *
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception 异常
     */
    @SneakyThrows
    public static String decryptEcb(String hexKey, String cipherText)  throws Exception{
        // 用于接收解密后的字符串
        String decryptStr;
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] srcData = decryptEcb(cipherData, keyData);
        // byte[]-->String
        decryptStr = new String(srcData, CHARSET_UTF_8);
        return decryptStr;
    }

    /**
     * sm4解密
     * 解密模式：采用ECB
     *
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception 异常
     */
    @SneakyThrows
    public static String decryptEcbs(String cipherText)  throws Exception{
        // 用于接收解密后的字符串
        String decryptStr;
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(keyRec);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] srcData = decryptEcb(cipherData, keyData);
        // byte[]-->String
        decryptStr = new String(srcData, CHARSET_UTF_8);
        return decryptStr;
    }
    /**
     * 解密
     *
     * @param cipherText byte[]
     * @param key        byte[]
     * @return byte[]
     * @throws Exception 异常
     */
    private static byte[] decryptEcb(byte[] cipherText, byte[] key) throws Exception {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static void main(String[] args)  throws Exception{
        String key = keyRec;
        String text = encryptEcb(key, "aaaaaa");
        System.out.println(text);
        System.out.println(decryptEcb(key, text));
    }
}