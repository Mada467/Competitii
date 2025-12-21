package competitii.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitate care reprezintă un utilizator al sistemului.
 * Include logica pentru autentificare, roluri și validarea email-ului instituțional.
 */
@Entity
@Table(name = "USERS") // Folosim USERS pentru a evita conflictele cu cuvintele rezervate din SQL
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false, length = 64) // SHA-256 produce un hash de exact 64 de caractere Hex
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role; // Roluri acceptate: STUDENT, REPRESENTATIVE

    /**
     * Relația către aplicațiile depuse de acest student.
     * Folosim ArrayList pentru inițializare pentru a evita NullPointerException.
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();

    public User() {
        // Constructor gol cerut de specificația JPA
    }

    // --- LOGICĂ DE BUSINESS SIMPLĂ ---

    /**
     * Verifică dacă utilizatorul deține o adresă de email instituțională validă.
     * @return true dacă email-ul aparține domeniului @student.upt.ro
     */
    public boolean hasInstitutionalEmail() {
        return email != null && email.toLowerCase().endsWith("@student.upt.ro");
    }

    // --- GETTERS & SETTERS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}