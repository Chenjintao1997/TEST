package com.cjt.test.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2019-11-18 11:01
 */
@RunWith(JUnit4.class)
public class ClassTest {

    @Test
    public void test1() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = "encryptKey";

        String content = "15515091290";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        Key secretKey = keyGenerator.generateKey();
        System.out.println(secretKey.getFormat());
        System.out.println(secretKey.getAlgorithm());
        byte[] secretKeyByte = secretKey.getEncoded();

        //System.out.println(new String(secretKeyByte));

        Key secretKeySpec = new SecretKeySpec(secretKeyByte, "AES");


        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        String encryptContent = new String(cipher.doFinal(content.getBytes()));

        Cipher cipherDecrypt = Cipher.getInstance("AES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec);
        String decryptContent = new String(cipher.doFinal(encryptContent.getBytes()));
        System.out.println(encryptContent);

        System.out.println(decryptContent);
    }


    @Test
    public void test2() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = "1111222233334444";  //密钥长度：128(default) 192 256
        String content = "15515091290";

        Key secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptByte = cipher.doFinal(content.getBytes());
        System.out.println(Arrays.toString(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptByte = decryptCipher.doFinal(encryptByte);
        System.out.println(new String(decryptByte));

    }

    @Test
    public void test2aa() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        String key = "1111222233334444";  //密钥长度：128(default) 192 256
        String content = "15515091290";
        IvParameterSpec ivParameterSpec = new IvParameterSpec("1234567890123456".getBytes());

        Key secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptByte = cipher.doFinal(content.getBytes());
        System.out.println(Arrays.toString(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decryptByte = decryptCipher.doFinal(encryptByte);
        System.out.println(new String(decryptByte));

    }

    @Test
    public void test2ab() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = "1111222233334444";  //密钥长度：128(default) 192 256
        String content = "15515091290";
        String content1 = "15515091291";

        Key secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptByte = cipher.doFinal(content.getBytes());
        byte[] encryptByte1 = cipher.doFinal(content1.getBytes());
        System.out.println(Arrays.toString(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte1));

        Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptByte = decryptCipher.doFinal(encryptByte);
        byte[] decryptByte1 = decryptCipher.doFinal(encryptByte1);
        System.out.println(new String(decryptByte));
        System.out.println(new String(decryptByte1));

    }


    @Test
    public void test2ac() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        String key = "1111222233334444";  //密钥长度：128(default) 192 256
        String content = "15515091290";
        String content1 = "15515091291";
        String iv = "1111222233334444";
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());

        Key secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptByte = cipher.doFinal(content.getBytes());
        byte[] encryptByte1 = cipher.doFinal(content1.getBytes());
        System.out.println(Arrays.toString(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte1));

        Cipher decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] decryptByte = decryptCipher.doFinal(encryptByte);
        byte[] decryptByte1 = decryptCipher.doFinal(encryptByte1);
        System.out.println(new String(decryptByte));
        System.out.println(new String(decryptByte1));

    }

    @Test
    public void test2a() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //String key = UUID.randomUUID().toString();
        String key = "123";
        String content = "15515091290";
        SecretKeySpec secretKeySpec = null;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(secureRandom.getProvider());
        System.out.println(secureRandom);
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(Base64.encodeBase64String(keyByte));

        secretKeySpec = new SecretKeySpec(keyByte, "AES");  //不会校验密钥长度
        byte[] keySpecByte = secretKeySpec.getEncoded();
        System.out.println(Base64.encodeBase64String(keySpecByte));


        Cipher cipherEncrypt = Cipher.getInstance("AES");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec);   //会校验密钥长度
        byte[] encryptByte = cipherEncrypt.doFinal(content.getBytes());
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher cipherDecrypt = Cipher.getInstance("AES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptByte = cipherDecrypt.doFinal(encryptByte);
        System.out.println(new String(decryptByte));


    }


    @Test
    public void test2ad() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //String key = UUID.randomUUID().toString();
        String key = "123";
        String content = "15515091290";
        SecretKeySpec secretKeySpec = null;
        IvParameterSpec ivParameterSpec = null;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(secureRandom.getProvider());
        System.out.println(secureRandom);
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(Base64.encodeBase64String(keyByte));

        secretKeySpec = new SecretKeySpec(keyByte, "AES");  //不会校验密钥长度
        byte[] keySpecByte = secretKeySpec.getEncoded();
        System.out.println(Base64.encodeBase64String(keySpecByte));


        Cipher cipherEncrypt = Cipher.getInstance("AES");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec);   //会校验密钥长度
        byte[] encryptByte = cipherEncrypt.doFinal(content.getBytes());
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher cipherDecrypt = Cipher.getInstance("AES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptByte = cipherDecrypt.doFinal(encryptByte);
        System.out.println(new String(decryptByte));


    }

    @Test
    public void test2b() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = UUID.randomUUID().toString();
        //String key = "123";
        String content = "15515091290";
        SecretKeySpec secretKeySpec = null;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(secureRandom.getProvider());
        System.out.println(secureRandom);
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(Base64.encodeBase64String(keyByte));

//        secretKeySpec = new SecretKeySpec(keyByte,"AES");
//        byte[] keySpecByte = secretKeySpec.getEncoded();
//        System.out.println(Base64.encodeBase64String(keySpecByte));
//        System.out.println(secretKeySpec.getFormat());

        Cipher cipherEncrypt = Cipher.getInstance("AES");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptByte = cipherEncrypt.doFinal(content.getBytes());
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher cipherDecrypt = Cipher.getInstance("AES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptByte = cipherDecrypt.doFinal(encryptByte);
        System.out.println(new String(decryptByte));

    }

    @Test
    public void test2c() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = UUID.randomUUID().toString();
        //String key = "123";
        String content = "15515091290";
        SecretKeySpec secretKeySpec = null;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(secureRandom.getProvider());
        System.out.println(secureRandom);
        keyGenerator.init(56, new SecureRandom(key.getBytes()));   //64(56) 最后获得得密钥长度为64位
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(Base64.encodeBase64String(keyByte));

//        secretKeySpec = new SecretKeySpec(keyByte,"AES");
//        byte[] keySpecByte = secretKeySpec.getEncoded();
//        System.out.println(Base64.encodeBase64String(keySpecByte));
//        System.out.println(secretKeySpec.getFormat());

        Cipher cipherEncrypt = Cipher.getInstance("DES");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptByte = cipherEncrypt.doFinal(content.getBytes());
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher cipherDecrypt = Cipher.getInstance("DES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptByte = cipherDecrypt.doFinal(encryptByte);
        System.out.println(new String(decryptByte));

    }

    @Test
    public void test2e() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = "11112222";
        String content = "15515091290";

        Key secretKeySpec = new SecretKeySpec(key.getBytes(), "DES");

        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptByte = cipher.doFinal(content.getBytes());
        System.out.println(Arrays.toString(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptByte = decryptCipher.doFinal(encryptByte);
        System.out.println(new String(decryptByte));

    }

    @Test
    public void test2f() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = UUID.randomUUID().toString();
        //String key = "123";
        String content = "15515091290";
//        String content = "15515091290155150912901551509129015515091290";
        SecretKeySpec secretKeySpec = null;

        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(secureRandom.getProvider());
        System.out.println(secureRandom);
        keyGenerator.init(56, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(Base64.encodeBase64String(keyByte));

//        secretKeySpec = new SecretKeySpec(keyByte,"AES");
//        byte[] keySpecByte = secretKeySpec.getEncoded();
//        System.out.println(Base64.encodeBase64String(keySpecByte));
//        System.out.println(secretKeySpec.getFormat());

        Cipher cipherEncrypt = Cipher.getInstance("DES");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptByte = cipherEncrypt.doFinal(content.getBytes());
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher cipherDecrypt = Cipher.getInstance("DES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptByte = cipherDecrypt.doFinal(encryptByte);
        System.out.println(new String(decryptByte));

    }


    @Test
    public void test2g() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = UUID.randomUUID().toString();
        //String key = "123";
        String content = "15515091290";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        SecureRandom secureRandom = new SecureRandom(key.getBytes());
        System.out.println(secureRandom.getAlgorithm());
        System.out.println(secureRandom.getProvider());
        System.out.println(secureRandom);
        keyGenerator.init(168, new SecureRandom(key.getBytes()));  //128(112)  192(168)-default  无论选择哪个，最后的密钥分组长度为固定的192位也就是24个字节
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(Base64.encodeBase64String(keyByte));

//        secretKeySpec = new SecretKeySpec(keyByte,"AES");
//        byte[] keySpecByte = secretKeySpec.getEncoded();
//        System.out.println(Base64.encodeBase64String(keySpecByte));
//        System.out.println(secretKeySpec.getFormat());

        Cipher cipherEncrypt = Cipher.getInstance("DESede");
        cipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptByte = cipherEncrypt.doFinal(content.getBytes());
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher cipherDecrypt = Cipher.getInstance("DESede");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptByte = cipherDecrypt.doFinal(encryptByte);
        System.out.println(new String(decryptByte));
    }

    @Test
    public void test2h() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String key = "111122223333444455556666";
        String content = "15515091290";

        Key secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");

        Cipher cipher = Cipher.getInstance("DESede");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptByte = cipher.doFinal(content.getBytes());
        System.out.println(Arrays.toString(encryptByte));
        System.out.println(Base64.encodeBase64String(encryptByte));

        Cipher decryptCipher = Cipher.getInstance("DESede");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptByte = decryptCipher.doFinal(encryptByte);
        System.out.println(new String(decryptByte));
    }


    @Test
    public void test3() {
        String str = "1";
        byte[] strByte = str.getBytes();
        System.out.println(Arrays.toString(strByte));

        System.out.println(Base64.encodeBase64String(strByte));
        ;

        byte[] testByte = {1};
        System.out.println(Arrays.toString(Base64.encodeBase64(testByte)));
        System.out.println(Base64.encodeBase64String(testByte));
    }

    @Test
    public void test4() {
        byte[] testByte = {33};
        System.out.println(new String(testByte));

        String str = "我";
        System.out.println(Arrays.toString(str.getBytes()));
        byte[] testByte1 = {-26, -120, -111};
        System.out.println(new String(testByte1));

        String str1 = "我爱你";
        System.out.println(Arrays.toString(str1.getBytes()));
        byte[] testByte2 = {-26, -120, -111, -25, -120, -79, -28, -67, -96};
        System.out.println(new String(testByte2));


    }

    @Test
    public void test5() {
        String str = "严";
        byte[] byteStr = str.getBytes();
        for (byte b : byteStr) {
            System.out.println(Integer.toBinaryString(b));
        }

    }

    @Test
    public void test6() {
        Integer i = Integer.valueOf("4E25", 16);
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));
    }

    @Test
    public void test7() {
        Random random1 = new Random(2);

        Random random2 = new Random(2);
        System.out.println(random1.nextInt());
        System.out.println(random1.nextInt());
        System.out.println(random2.nextInt());
        System.out.println(random2.nextInt());

    }

    @Test
    public void test8() {
        String key = "1111222233334444";

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        byte[] byte1 = secretKeySpec.getEncoded();
        System.out.println(new String(byte1));

    }

    @Test
    public void test9() throws NoSuchAlgorithmException {
        String key = "1111222233334444";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        System.out.println(new String(keyByte));
        System.out.println(Base64.encodeBase64String(keyByte));
    }


    @Test
    public void test10() {
        Random random1 = new Random(0);

        Random random2 = new Random(0);
        byte[] bytes1 = new byte[5];
        byte[] bytes2 = new byte[5];
        random1.nextBytes(bytes1);
        random2.nextBytes(bytes2);
        System.out.println(Arrays.toString(bytes1));
        System.out.println(Arrays.toString(bytes2));
    }

    @Test
    public void test10a() {
        byte[] bytes = "1".getBytes();
        SecureRandom secureRandom1 = new SecureRandom(bytes);
        SecureRandom secureRandom2 = new SecureRandom(bytes);
        byte[] bytes1 = new byte[5];
        byte[] bytes2 = new byte[5];
        secureRandom1.nextBytes(bytes1);
        secureRandom2.nextBytes(bytes2);
        System.out.println(Arrays.toString(bytes1));
        System.out.println(Arrays.toString(bytes2));
    }

    @Test
    public void test10b() {
        byte[] bytes = "1".getBytes();
        SecureRandom secureRandom1 = new SecureRandom(bytes);
        SecureRandom secureRandom2 = new SecureRandom(bytes);
        int i1 = secureRandom1.nextInt();
        int i2 = secureRandom2.nextInt();
        System.out.println(i1 + " " + i2);

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 10000000; i++) {
            set.add(secureRandom1.nextInt());
        }
        System.out.println(set.size());
    }

    @Test
    public void test10c() {
        Random random = new Random(1);
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < 10000000; i++) {
            set.add(random.nextInt());
        }
        System.out.println(set.size());
    }

    @Test
    public void test10d() {
        Random random = new Random(1);
        Random random1 = new Random(1);
        for (int i = 0; i < 10; i++) {
            System.out.print(random.nextInt() + " ");
        }
        System.out.println();
        for (int i = 0; i < 10; i++) {
            System.out.print(random1.nextInt() + " ");
        }
    }

    @Test
    public void test10e() {
        Random random = new Random();
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");
        System.out.println(set.toArray()[random.nextInt(4)]);
        System.out.println(set.toArray()[random.nextInt(4)]);
        System.out.println(set.toArray()[random.nextInt(4)]);
        System.out.println(set.toArray()[random.nextInt(4)]);
    }


    @Test
    public void test11() throws NoSuchAlgorithmException {
        String key = "1111222233334444";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        SecretKey secretKey1 = keyGenerator.generateKey();
        byte[] keyByte1 = secretKey1.getEncoded();

        System.out.println(new String(keyByte));
        System.out.println(Base64.encodeBase64String(keyByte));

        System.out.println(new String(keyByte1));
        System.out.println(Base64.encodeBase64String(keyByte1));
    }


    @Test
    public void testSM4() throws NoSuchAlgorithmException {
        String key = "1111222233334444";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("SM4");
        keyGenerator.init(128, new SecureRandom(key.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyByte = secretKey.getEncoded();

        SecretKey secretKey1 = keyGenerator.generateKey();
        byte[] keyByte1 = secretKey1.getEncoded();

        System.out.println(new String(keyByte));
        System.out.println(Base64.encodeBase64String(keyByte));

        System.out.println(new String(keyByte1));
        System.out.println(Base64.encodeBase64String(keyByte1));
    }

    @Test
    public void test12() {
        System.out.println(Base64.encodeBase64String("0000000000000000".getBytes()));
    }

    @Test
    public void testSM4a() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        String key = "0000000000000000";
        String content = "15515091290";

        byte[] bytes = SM4Util.encrypt_Ecb_Padding(key.getBytes(), content.getBytes());
        System.out.println(Hex.encodeHexString(bytes));
        System.out.println(Base64.encodeBase64String(bytes));
    }

    @Test
    public void testSM4b() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException {
        String key = "0000000000000000";
        String content = "15515091290";
        String iv = "0123456789123456";

        byte[] bytes = SM4Util.encrypt_Cbc_Padding(key.getBytes(), iv.getBytes(), content.getBytes());
        System.out.println(Hex.encodeHexString(bytes));
        System.out.println(Base64.encodeBase64String(bytes));
    }


    public enum testEnum {
        TEST1(1),
        TEST2(2);

        int value;

        testEnum(int i) {
            this.value = i;
        }


    }

    @Test
    public void testEnum1() {
        testEnum str = testEnum.TEST1;
        System.out.println(str.ordinal());
        System.out.println(str.name());
        System.out.println(testEnum.TEST1.value);
        System.out.println(testEnum.TEST2.value);
        System.out.println(str);

        System.out.println(TestEnum2.TEST1);
    }

    @Test
    public void testEnum2() {
        System.out.println(TestEnum2.valueOf("TEST1").value);
        System.out.println(Arrays.toString(TestEnum2.values()));
    }

    @Test
    public void testEnum3() {
        System.out.println(TestEnum3.getStr(1));
    }


    @Test
    public void test13() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String content1 = "15515091290";
        byte[] keyBytes = SecureUtil.createSecureKeyBytes("AES", 128);
        System.out.println(Base64.encodeBase64String(keyBytes));
        SecureUtil secureUtil = new SecureUtil("AES", "ECB", "ISO10126Padding", keyBytes);
        secureUtil.init(OpMode.ENCRYPT);
        byte[] encryptContent1 = secureUtil.option(content1.getBytes());

        System.out.println(Base64.encodeBase64String(encryptContent1));
    }

    @Test
    public void test14() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String content1 = "15515091290";
        byte[] keyBytes = SecureUtil.createSecureKeyBytes("DES", 56);
        System.out.println(Base64.encodeBase64String(keyBytes));
        System.out.println(Hex.encodeHexString(keyBytes));
        SecureUtil secureUtil1 = new SecureUtil("DES", "ECB", keyBytes);
        secureUtil1.init(OpMode.ENCRYPT);
        byte[] encryptContent1 = secureUtil1.option(content1.getBytes());
        System.out.println(Base64.encodeBase64String(encryptContent1));

        SecureUtil secureUtil2 = new SecureUtil("DES", "ECB", keyBytes);
        secureUtil2.init(OpMode.DECRYPT);
        byte[] decryptContent2 = secureUtil2.option(encryptContent1);
        System.out.println(new String(decryptContent2));


    }

    @Test
    public void test15() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String content1 = "15515091290";
        String key = "1111222233334444";
        String iv = "1234567890123456";

        byte[] keyBytes = SecureUtil.createSecureKeyBytes("SM4", 128);
        //byte[] keyBytes = key.getBytes();
//        byte[] ivBytes = SecureUtil.createIvBytes(16);
        byte[] ivBytes = iv.getBytes();
        System.out.println("key: " + Base64.encodeBase64String(keyBytes));
        System.out.println(Hex.encodeHexString(keyBytes));

        System.out.println("iv:" + Base64.encodeBase64String(ivBytes));
        System.out.println(Hex.encodeHexString(ivBytes));
        System.out.println(iv);

        SecureUtil secureUtil1 = new SecureUtil("SM4", "CBC", "PKCS7Padding", keyBytes, ivBytes);
        secureUtil1.init(OpMode.ENCRYPT);
        byte[] encryptContent1 = secureUtil1.option(content1.getBytes());
        System.out.println(Base64.encodeBase64String(encryptContent1));

        SecureUtil secureUtil2 = new SecureUtil("SM4", "CBC", "PKCS7Padding", keyBytes, ivBytes);
        secureUtil2.init(OpMode.DECRYPT);
        byte[] decryptContent2 = secureUtil2.option(encryptContent1);
        System.out.println(new String(decryptContent2));
    }

    @Test
    public void test16() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String content1 = "15515091290";
        String key = "1111222233334444";
        String iv = "1234567890123456";
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = iv.getBytes();

        // byte[] keyBytes = SecureUtil.createSecureKeyBytes("AES", 128);
        System.out.println(Base64.encodeBase64String(keyBytes));
        System.out.println(Hex.encodeHexString(keyBytes));
        SecureUtil secureUtil1 = new SecureUtil("AES", "CFB", keyBytes, ivBytes);
        secureUtil1.init(OpMode.ENCRYPT);
        byte[] encryptContent1 = secureUtil1.option(content1.getBytes());
        System.out.println(Base64.encodeBase64String(encryptContent1));

        SecureUtil secureUtil2 = new SecureUtil("AES", "CFB", keyBytes, ivBytes);
        secureUtil2.init(OpMode.DECRYPT);
        byte[] decryptContent2 = secureUtil2.option(encryptContent1);
        System.out.println(new String(decryptContent2));
    }

    @Test
    public void test17() throws UnsupportedEncodingException {
        String str = "15515091290";
        System.out.println(Arrays.toString(str.getBytes()));
        System.out.println(new String(str.getBytes(), "GBK"));
    }

    @Test
    public void test18() {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        List<Integer> list2 = (List<Integer>) ((ArrayList<Integer>) list1).clone();
        System.out.println(System.identityHashCode(list1));
        System.out.println(System.identityHashCode(list2));

        String str1 = "123";
        String str2 = str1.trim();
    }

    @Test
    public void test19() throws UnsupportedEncodingException {
        System.out.println(new String("i_10000_20200427_VGOP1-R2.10-98201_00_001.dat€174€4€20200427€20200427100001".getBytes(),
                "GBK"));
        System.out.println(new String(("1€20200427€1001€一汽€4E95039529D6AABC2CB00784486A6738\n" +
                "2€20200427€1001€一汽€D050FBDFAFB1AD536DD6D76EBD7103C6\n" +
                "3€20200427€1001€一汽€F0BF6445225BD1595C75E2250AE8E5C0\n" +
                "4€20200427€1001€一汽€5FA73435927DF865B84938AE172B5D27").getBytes(),
                "GBK"));

        byte[] arr = ("一").getBytes();
        System.out.println(Arrays.toString(arr));
        byte[] arr2 = "一".getBytes();
        System.out.println(Arrays.toString(arr2));
        String str2 = new String(arr2, "GBK");
        System.out.println(str2);
        System.out.println(Arrays.toString(str2.getBytes()));
        System.out.println(Arrays.toString(str2.getBytes("GBK")));
        System.out.println(new String(str2.getBytes("GBK"), "UTF-8"));

    }

    @Test
    //一
    public void test20() throws UnsupportedEncodingException {
        String str = "一";
//        byte[] arr1 = str.getBytes();
//        System.out.println(Arrays.toString(str.toCharArray()));
//        System.out.println(Arrays.toString(arr1));
//        System.out.println(new String(arr1,"UTF-8"));

        System.out.println(str);
        byte[] arr1 = str.getBytes("GBK");
        System.out.println(Arrays.toString(arr1));

        System.out.println(str);
//        byte[] arr2 = str.getBytes("GBK");
        byte[] arr2 = {-46, -69};
        System.out.println(Arrays.toString(arr2));
        System.out.println(new String(arr2, "GBK"));


        byte[] arr3 = str.getBytes("UNICODE");
        System.out.println(Arrays.toString(arr3));
        System.out.println(new String(arr3, "UNICODE"));

        System.out.println("-------");
        byte[] arr4 = str.getBytes("UTF-8");
        System.out.println(Arrays.toString(arr4));
        String str4 = new String(arr4, "ISO8859-1");
        System.out.println(str4);
        byte[] arr4a = str4.getBytes("ISO8859-1");
        System.out.println(Arrays.toString(arr4a));
        String str4a = new String(arr4a, "UTF-8");
        System.out.println(str4a);

    }

    @Test
    public void test21() {
        String str = null;
        String str2 = "123";
        String str3 = "";

        System.out.println(str + str2);
        System.out.println(str2 + str3);

        System.out.println(DigestUtils.md5Hex(str2));
        System.out.println(DigestUtils.md5Hex(str + str2));
        System.out.println(DigestUtils.md5Hex(str2 + str3));
        System.out.println(DigestUtils.md5Hex("abc"));
        System.out.println(DigestUtils.md5Hex("ABC"));
        System.out.println(DigestUtils.md5Hex("123456123"));
    }
}

enum TestEnum2 {
    TEST1(1),
    TEST2(2);

    int value;

    TestEnum2(int i) {
        this.value = i;
    }
}


enum TestEnum3 {
    TEST1(1, "t1"),
    TEST2(2, "t2");

    int value;

    String str;

    TestEnum3(int i, String str) {
        this.value = i;
        this.str = str;
    }

    public static String getStr(int value) {
        String returnStr = null;
        for (TestEnum3 testEnum3 : TestEnum3.values()) {
            if (testEnum3.value == value) {
                returnStr = testEnum3.str;
                break;
            }
        }
        return returnStr;
    }
}
