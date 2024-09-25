/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Beans/Bean.java to edit this template
 */
package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Asus
 */
public class Segurdad  {
    
     public static String encriptarSHA256(String contraseña) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Convertir la contraseña a bytes usando UTF-8 para asegurar la codificación correcta
            byte[] encodedHash = digest.digest(contraseña.getBytes("UTF-8"));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }  
    
}
