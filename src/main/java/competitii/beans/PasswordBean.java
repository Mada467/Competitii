package competitii.beans;

import jakarta.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean responsabil pentru securizarea parolelor prin hashing.
 * Folosim SHA-256 pentru a nu stoca parolele în clar în baza de date.
 */
@Stateless
public class PasswordBean {

    private static final Logger LOGGER = Logger.getLogger(PasswordBean.class.getName());

    public String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return null;
        }

        try {
            // Algoritmul SHA-256 este standard pentru hashing de securitate medie
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Folosim StandardCharsets.UTF_8 pentru a evita problemele cu caracterele speciale pe diferite OS-uri
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            return bytesToHex(encodedHash);

        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, "Algoritmul de hashing nu a fost găsit", ex);
            return null;
        }
    }

    /**
     * Transformă un array de bytes într-un String Hexadecimal.
     * Am separat această logică pentru a face metoda principală mai ușor de citit.
     */
    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            // "%02x" formatează automat octetul în hex cu 2 cifre (adaugă 0 dacă e nevoie)
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}