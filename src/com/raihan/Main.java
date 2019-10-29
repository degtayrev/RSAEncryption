package com.raihan;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    //Put your Private and public Key here
    private static String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAshotzJEpjxQy3G8vNs7LIeJuhan5F1YUXA6hSDDmQsL9Bqze/dcUngLcz7e2mlgNShxd0JhnoxtVCVm5j28yo9e1scf3jlXNPYoN04oyDWjdLgkV5102dgCPfKD324ghWnmkwEGhMlzqiowiebanHqNvr7ba8CA1ZC4thpPvxioFXVutuXejkVBdxn+JNDiDBYCMDHJxE4yNzFcalPocFxFiwblL5w78V5gzvUX7//i60gJyAQw/weGd52GyMu37xNmGzidxRfY+Wsbf3x2vA1mP1NO9NI3XqUCpSlWkBqxIKZ8GBCOOsEVO/52KuMRkSVrbR+QYstyuZl1vOfyNGTCom1pFV0he25z/3UPCyvrqk4ID9OHftjluA61UlgBGTQVWBQi4/daGiBrd59X/Owmm9w+nAku9QpUPppO8+qgVIMk8/r7GKEhZBt+e1mmPduubn66q8xJOMq5TFL/6whR6yQzEVdOtQ7TEhb3Knt3iKHMHKpvWRGVvd8/1Pk9u6dgiM1UAJ/GEkxJpd0judTV06kmuYvhVDprDe08eQRI8CpbcQUyOOBkKNZJ9h5olf2AJopg/wGw0IZncU6RirSoFRZkz2JCPhv3SVA6rK0Xf2WHVVHEWJ1qSnbUIKc+3lU583YHlyzTvZCiblgJThaTurzEQ3atkzzdqoUcikC0CAwEAAQ==";
    private static String privateKey = "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";
    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public static String getMd5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        try {
            String data = "Raihan";
            //Hash MD5
            String hash = getMd5(data);
            System.out.println("Hash Text = " + hash);

            // RSA Encryption
            String encryptedString = Base64.getEncoder().encodeToString(encrypt(hash, publicKey));
            System.out.println("Encrypted Hash  = " + encryptedString);

            // RSA Description
            String decryptedString = Main.decrypt(encryptedString, privateKey);
            System.out.println("Decrypted Hash = " + decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}
