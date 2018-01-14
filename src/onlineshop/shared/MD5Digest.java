package onlineshop.shared;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class MD5Digest {
    private static final Logger LOGGER = Logger.getLogger(MD5Digest.class.getName());

    public String digest(String original) {
        String digested = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(original.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digested = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.severe(e.getMessage());
        }
        return digested;
    }
}