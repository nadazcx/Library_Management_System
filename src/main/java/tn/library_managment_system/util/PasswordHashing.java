package tn.library_managment_system.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {
    public static String hashPasswordSha256(String originalString) throws NoSuchAlgorithmException {

        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashbytes);
    }
    private static String bytesToHex(byte[] hashbytes) {
        StringBuilder hexString = new StringBuilder(2 * hashbytes.length);
        for (int i = 0; i < hashbytes.length; i++) {
            String hex = Integer.toHexString(0xff & hashbytes[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
