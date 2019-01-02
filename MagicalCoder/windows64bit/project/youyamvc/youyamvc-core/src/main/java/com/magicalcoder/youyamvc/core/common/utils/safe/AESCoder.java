package com.magicalcoder.youyamvc.core.common.utils.safe;

import com.magicalcoder.youyamvc.core.common.utils.HexUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @ClassName AESCoder
 * @Description AES加密解密组件
 * @author lijh@fund123.cn
 * @date 2013年10月23日
 */
public class AESCoder {

    public static final String KEY_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @return
     */
    public static byte[] encrypt(String content, String password) {
        try {
            byte[] enCodeFormat = initKey(password);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] enCodeFormat = initKey(password);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypts(byte[] content, String password){
        return new String(decrypt(content,password));
    }
    private static byte[] initKey(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);
        kgen.init(128, new SecureRandom(password.getBytes()));
        SecretKey secretKey = kgen.generateKey();
        System.out.println(new String(secretKey.getEncoded(),"utf-8"));
        return secretKey.getEncoded();
//        return "1234567890123456".getBytes("ASCII");
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
        Date now = new Date();
        String content = "from=magicalcoder&time=1396435742498";
        String password = "password";
//加密
        System.out.println("加密前：" + content);
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = HexUtils.parseByte2HexStr(encryptResult);
        System.out.println("加密后：" + encryptResultStr);
//解密

        encryptResultStr ="F5800EBCD6DF3CBD2B601D51A7E185DE67202A9CDF5A40851B5B2E4A5453E1";
        byte[] decryptFrom = HexUtils.parseHexStr2Byte(encryptResultStr);
        byte[] decryptResult = decrypt(decryptFrom,password);
        System.out.println("解密后：" + new String(decryptResult));
    }
}
