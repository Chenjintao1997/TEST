package com.cjt.test.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.UUID;

/**
 * @Author: chenjintao
 * @Date: 2019-11-21 11:01
 */
public class SecureUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(SecureUtil.class);

    private byte[] keyBytes;

    private String encryptType;  //加密类型

    private String encryptMode;  //加密模式

    private String padding = "NoPadding";     //填充类型

    private IvParameterSpec iv;  //初始化向量

    private byte[] ivBytes;

    private String transformation;

    private Cipher cipher; //加解密核心类

    private Key key;       //密钥

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    protected SecureUtil() {
    }

    public SecureUtil(String encryptType, String encryptMode, byte[] keyBytes) {
        this(encryptType, encryptMode, null, keyBytes, null);
    }


    public SecureUtil(String encryptType, String encryptMode, String padding, byte[] keyBytes) {
        this(encryptType, encryptMode, padding, keyBytes, null);

    }

    public SecureUtil(String encryptType, String encryptMode, byte[] keyBytes, byte[] ivBytes) {
        this(encryptType, encryptMode, null, keyBytes, ivBytes);

    }

    public SecureUtil(String encryptType, String encryptMode, String padding, byte[] keyBytes, byte[] ivBytes) {
        this.encryptType = encryptType;
        this.encryptMode = encryptMode;
        this.keyBytes = keyBytes;
        this.ivBytes = ivBytes;
        if (StringUtils.isNotBlank(padding))
            this.padding = padding;
    }

    //初始化Key Iv Cipher
    public SecureUtil init(OpMode opMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        this.key = new SecretKeySpec(keyBytes, encryptType);
        this.transformation = encryptType + "/" + encryptMode + "/" + padding;
        this.cipher = Cipher.getInstance(transformation);

        if (ivBytes != null && ivBytes.length > 0) {
            this.iv = new IvParameterSpec(ivBytes);
            cipher.init(opMode.value, key, iv);
        } else cipher.init(opMode.value, key);
        return this;
    }


    //返回byte数组
    public byte[] option(byte[] content) throws BadPaddingException, IllegalBlockSizeException {
        return cipher.doFinal(content);
    }

    //加密返回BASE64格式的内容
    public String encryptRtBase64(byte[] content) throws BadPaddingException, IllegalBlockSizeException {
        return Base64.encodeBase64String(option(content));
    }


    //随机生成安全密钥
    public static byte[] createSecureKeyBytes(String encryptType, String seed, int keyBitSize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptType);
        keyGenerator.init(keyBitSize, new SecureRandom(seed.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    //随机生成安全密钥
    public static byte[] createSecureKeyBytes(String encryptType, int keyBitSize) throws NoSuchAlgorithmException {
        String seed = UUID.randomUUID().toString();
        return createSecureKeyBytes(encryptType, seed, keyBitSize);
    }

    //随机生成安全向量
    public static byte[] createIvBytes(String seed, int ivSize) {
        SecureRandom secureRandom = new SecureRandom(seed.getBytes());
        byte[] bytes = new byte[ivSize];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    //随机生成安全向量
    public static byte[] createIvBytes(int ivSize) {
        String seed = UUID.randomUUID().toString();
        return createIvBytes(seed, ivSize);
    }

    //通过指定编码集编码某字符串
    public static byte[] encode(String content, String charset) throws DecoderException, UnsupportedEncodingException {
        String upperCase = charset.toUpperCase();
        byte[] bytes;
        switch (upperCase) {
            case "BASE64":
                bytes = Base64.decodeBase64(content);
                break;
            case "HEX":
                bytes = Hex.decodeHex(content);
                break;
            default:
                bytes = content.getBytes(charset);
        }
        return bytes;
    }
}
