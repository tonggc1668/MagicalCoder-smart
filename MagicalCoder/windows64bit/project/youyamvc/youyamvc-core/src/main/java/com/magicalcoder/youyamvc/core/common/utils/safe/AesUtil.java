package com.magicalcoder.youyamvc.core.common.utils.safe;

/**
 * Created by hzwww.magicalcoder.com on 2015/10/16.
 * http://outofmemory.cn/code-snippet/35524/AES-with-javascript-java-csharp-python-or-php
 */
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    private static final int IV_LEN = 16;
    public static String buildIvSub(String str){
        String template = "0000000000000000";
        if(str==null|| "".equals(str)){
            return template;
        }
        if(str.length()>=IV_LEN){
            return str.substring(0,IV_LEN);
        }
        return str+template.substring(0,IV_LEN-str.length());
    }


    public static void main(String[] args) {
        String source = "{\"c\": \"\", \"g\": 0, \"n\": \"\", \"p\": \"\", \"ex\": , \"id\": }";
        String password = "1234567812345678";
        String iv = "wVQXbr1cbRYM6IMx";
        String aesStr = encrypt(source, password, iv);
        System.out.println(aesStr);
        aesStr = "/vqx4o+w8kG9x5V7ts6H97OH6qRLUYnNrVwUA8nb3KOE83rnlMYn2LE5ZZGjKq8jQ07CaG3T4uYL8oXQUnCXftIH46Q5K9DMtdQBW6cQpMTIZ2q/ayMOZj0gDZDO9KwH";
        System.out.println(desEncrypt(aesStr,password,iv));
    }

    /**
     *
     * @param source 原始字符串
     * @param password  秘钥 16位
     * @param iv 随机约定的 16位 一般用用户id cookie前16位不足16位补0 等等双方都知道的 但是又会变化的最安全
     * @return
     */
    public static String encrypt(String source,String password,String iv) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = source.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(password.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new sun.misc.BASE64Encoder().encode(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密异常");
        }
    }

    /**
     *
     * @param aesStr 加密串
     * @param password 秘钥 16位
     * @param iv 随机约定的 16位 一般用用户id cookie前16位不足16位补0 等等双方都知道的 但是又会变化的最安全
     * @return
     */
    public static String desEncrypt(String aesStr,String password,String iv) {
        try {
            byte[] encrypted1 = new sun.misc.BASE64Decoder().decodeBuffer(aesStr);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(password.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密异常");
        }
    }
}
