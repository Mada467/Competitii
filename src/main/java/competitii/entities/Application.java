package competitii.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Reprezintă înscrierea unui student la o competiție.
 * Conține informații despre starea aplicației și scorul obținut.
 */
@Entity
@Table(name = "APPLICATIONS")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Folosim EAGER pentru student deoarece aproape întotdeauna avem nevoie de numele lui
    // când afișăm o listă de înscrieri.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User student;

    // Folosim LAZY pentru competiție pentru a evita încărcarea întregului obiect
    // dacă avem deja ID-ul (optimizare de performanță).
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPETITION_ID", nullable = false)
    private Competition competition;

    @Column(nullable = false)
    private String status; // Valori posibile: PENDING, ACCEPTED, REJECTED

    private Double score;

    @Column(name = "APPLY_DATE")
    private LocalDateTime applyDate;

    @Column(length = 1000)
    private String additionalInfo;

    public Application() {
        // Constructor gol obligatoriu pentru JPA
    }

    /**
     * Metodă apelată automat de JPA înainte de salvarea în baza de date.
     * Ne asigurăm că applyDate nu este niciodată null.
     */
    @PrePersist
    protected void onCreate() {
        if (this.applyDate == null) {
            this.applyDate = LocalDateTime.now();
        }
    }

    // Getters și Setters
    // (Păstrăm structura standard, dar adăugăm spațiere pentru lizibilitate)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public LocalDateTime getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDateTime applyDate) {
        this.applyDate = applyDate;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}