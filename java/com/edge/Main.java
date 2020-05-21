package com.edge;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;


public class Main {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static void main(String[] args) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String origin = "Leandre-ekPR9pfLln4WuNshIF6gEa8TX3D7iHCm";
        String key = "QykzJbTiofFY1escM9gOjLEwI0NntZH7";
        String encrypted = "JgnFh3APEX0Db6qjWutkMUZJZqThFOhkosCsejF2wtdx6QIK6AaK9OLlkVc9ktVbDVMyGoqwoEgHOrer8SA0Eg==";

        String s = Encrypt(origin, key);
        System.out.println(s);

        String o = Decrypt(s, key);
        System.out.println(o.contentEquals(origin));

        String o1 = Decrypt(encrypted, key);
        System.out.println(o1);
        System.out.println(o1.contentEquals(origin));
    }


    public static String Encrypt(String plainText, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);

        Random rand = new SecureRandom();
        byte[] bytes = new byte[16];
        rand.nextBytes(bytes);

        IvParameterSpec ivSpec = new IvParameterSpec(bytes);

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] allByteArray = new byte[ivSpec.getIV().length + encrypted.length];

        ByteBuffer buff = ByteBuffer.wrap(allByteArray);

        buff.put(ivSpec.getIV());

        buff.put(encrypted);

        byte[] combined = buff.array();

        return new String(Base64.getEncoder().encode(combined), StandardCharsets.UTF_8);
    }

    public static String Decrypt(String cipherText, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] encryptBytes = Base64.getDecoder().decode(cipherText);
        byte[] ivBytes = new byte[16];
        byte[] cipherBytes = new byte[encryptBytes.length - 16];

        System.arraycopy(encryptBytes, 0, ivBytes, 0, 16);
        System.arraycopy(encryptBytes, 16, cipherBytes, 0, encryptBytes.length - 16);

        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);

        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(cipherBytes);

        return new String(original, StandardCharsets.UTF_8);
    }
}