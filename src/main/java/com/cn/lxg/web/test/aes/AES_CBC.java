package com.cn.lxg.web.test.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
/**
 * AES/CBC/PKCS5Padding 对称加密
 * @author jia
 *
 */
public class AES_CBC {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * 数据加密
     * @param srcData
     * @param key
     * @param iv
     * @return
     */
    public static String encrypt(String srcData,byte[] key,byte[] iv)
    {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        String encodeBase64String = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] encData = cipher.doFinal(srcData.getBytes());
            encodeBase64String = Base64.encodeBase64String(encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeBase64String;
    }

    /**
     * 数据解密
     * @param encDataStr
     * @param key
     * @param iv
     * @return
     */
    public static String decrypt(String encDataStr,byte[] key,byte[] iv)
    {
        byte[] encData = Base64.decodeBase64(encDataStr);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        byte[] decbbdt = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            decbbdt = cipher.doFinal(encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decbbdt);
    }

    public static void main(String[] args) throws Exception {
        String str = "fot4Ginq4iey7kLUUmA+dA==";
        byte[] s = Base64.decodeBase64(str);
        String s2 = "12345678ilkljklkjv";
        String iv = "1234567890123456";
        System.out.println("加密前： "+s2);
        String encrypt = AES_CBC.encrypt(s2, s, iv.getBytes());
        System.out.println("加密后： "+new String(encrypt));
        String decrypt = AES_CBC.decrypt(encrypt, s, iv.getBytes());
        System.out.println("解密后： "+decrypt);
    }
}
