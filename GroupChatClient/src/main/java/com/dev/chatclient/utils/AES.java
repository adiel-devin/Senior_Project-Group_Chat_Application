package com.dev.chatclient.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/*
 *AES - Advanced Encryption Algorithm
 * Used from the website https://howtodoinjava.com/java/java-security/java-aes-encryption-example/
 * The code was used for simple string encryption for messages in the chatlog and directmessages.
 * The code was used as is, no changes were made by myself. It fit what I need for simple String encryption with key
 */

public class AES
{
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try
        {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();;
        }
    }

    public static String encrypt(String strToEncrypt, String groupId)
    {
        try
        {
            setKey(groupId);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch(Exception e)
        {
            System.out.println("Error with encryption!" + e);
        }
        return null;
    }

    public static String decrypt(String strToDecrypt, String groupId)
    {
        try
        {
            setKey(groupId);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch(Exception e)
        {
            System.out.println("Error with decryption: " + e);
        }
        return null;
    }

}
