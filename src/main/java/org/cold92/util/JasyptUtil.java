package org.cold92.util;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptUtil {

    // 加密器
    private static BasicTextEncryptor encryptor;
    // 加盐
    private static String SALT = "123456!@#";

    static {
        encryptor = new BasicTextEncryptor();
        encryptor.setPassword(SALT);
    }

    /**
     * 加密密码
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        return encryptor.encrypt(password);
    }

    /**
     * 解密密码
     * @param password
     * @return
     */
    public static String decyptPassword(String password) {
        return encryptor.decrypt(password);
    }
}
