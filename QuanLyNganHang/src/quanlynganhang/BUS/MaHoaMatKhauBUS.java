/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.BUS;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author THAI
 */
public class MaHoaMatKhauBUS {

    public static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(password.getBytes());

            byte[] byteData = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte b : byteData) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return null;
        }

    }

    public static boolean checkPassword(String encryptedPassword, String inputPassword) {
        return encryptPassword(inputPassword).equals(encryptedPassword);
    }
}
