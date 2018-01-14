package online_shop.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Digest {

    public MD5Digest() {
    }

    public String digest(String original) {
        String digested = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digested = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digested;
    }
}