package competitii.beans;

import competitii.entities.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean responsabil pentru gestionarea utilizatorilor și a procesului de autentificare.
 */
@Stateless
public class UsersBean {

    private static final Logger LOGGER = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Inject
    private PasswordBean passwordBean;

    public boolean createUser(String username, String email, String password, String role) {
        // Bună practică: Verificăm imediat dacă username-ul este deja ocupat
        if (findByUsername(username) != null) {
            LOGGER.log(Level.INFO, "Tentativă de creare cont duplicat: {0}", username);
            return false;
        }

        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            // Hash-uim parola înainte de salvare pentru securitate
            user.setPassword(passwordBean.hashPassword(password));

            // Validare simplă a rolului pentru a asigura consistența datelor în DB
            String finalRole = (role != null) ? role.toUpperCase() : "STUDENT";
            user.setRole(finalRole);

            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la persistența utilizatorului: " + username, e);
            return false;
        }
    }

    /**
     * Verifică credențialele utilizatorului.
     * @return Obiectul User dacă datele sunt corecte, null în caz contrar.
     */
    public User authenticate(String username, String password) {
        try {
            String hashedPassword = passwordBean.hashPassword(password);

            // Folosim query-ul pentru a găsi potrivirea exactă (user + hash)
            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
                    .setParameter("user", username)
                    .setParameter("pass", hashedPassword)
                    .getSingleResult();

        } catch (NoResultException ex) {
            // Nu logăm parola, doar faptul că autentificarea a eșuat
            LOGGER.log(Level.WARNING, "Autentificare eșuată pentru user: {0}", username);
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                    .setParameter("user", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Este normal să nu găsim user-ul uneori (ex: la înregistrare), deci nu logăm eroare
            return null;
        }
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
}