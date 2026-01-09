/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packetworldjavafx.utilidades;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author ian_e
 */
public class PasswordHasher {
    private static final int ITERACIONES = 600000;
    private static final int LONGITUD_LLAVE = 256;
    private static final String ALGORITMO_USADO = "PBKDF2WithHmacSHA256";

    public static String hash(String password) throws Exception {
       
        byte[] salt = new byte[16];
        SecureRandom.getInstanceStrong().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERACIONES, LONGITUD_LLAVE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO_USADO);
        byte[] hash = factory.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }
    
    
    public static boolean verificar(String password, String valorAlmacenado) throws Exception {
        String[] parts = valorAlmacenado.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] storedHash = Base64.getDecoder().decode(parts[1]);

        // Re-hash the provided password with the original salt
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERACIONES, LONGITUD_LLAVE);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITMO_USADO);
        byte[] newHash = factory.generateSecret(spec).getEncoded();

        // Constant-time comparison to prevent timing attacks
        int diff = storedHash.length ^ newHash.length;
        for (int i = 0; i < storedHash.length && i < newHash.length; i++) {
            diff |= storedHash[i] ^ newHash[i];
        }
        return diff == 0;
    }
    
}
