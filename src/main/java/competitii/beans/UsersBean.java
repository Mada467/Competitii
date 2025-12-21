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

@Stateless
public class UsersBean {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Inject
    private PasswordBean passwordBean;

    private static final Logger LOGGER = Logger.getLogger(UsersBean.class.getName());

    public boolean createUser(String username, String email, String password, String role) {
        // Verificăm dacă utilizatorul există deja (Cerința: Unique User)
        if (findByUsername(username) != null) {
            return false;
        }

        try {
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            // Securizare parolă
            String hashedPassword = passwordBean.hashPassword(password);
            user.setPassword(hashedPassword);

            // Validare Rol (STUDENT, ADMIN, REPRESENTATIVE)
            user.setRole(role.toUpperCase());

            entityManager.persist(user);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la crearea utilizatorului", e);
            return false;
        }
    }

    public User authenticate(String username, String password) {
        try {
            String hashedPassword = passwordBean.hashPassword(password);

            return entityManager.createQuery(
                            "SELECT u FROM User u WHERE u.username = :user AND u.password = :pass", User.class)
                    .setParameter("user", username)
                    .setParameter("pass", hashedPassword)
                    .getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.log(Level.WARNING, "Autentificare esuata pentru: {0}", username);
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :user", User.class)
                    .setParameter("user", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}