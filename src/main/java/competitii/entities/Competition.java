package competitii.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entitate care definește o competiție și regulile de înscriere.
 */
@Entity
@Table(name = "COMPETITIONS")
public class Competition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(name = "MIN_PARTICIPANTS")
    private Integer minParticipants;

    @Column(name = "MAX_PARTICIPANTS")
    private Integer maxParticipants;

    @Column(name = "APPLICATION_START")
    private LocalDateTime applicationStart;

    @Column(name = "APPLICATION_DEADLINE")
    private LocalDateTime applicationDeadline;

    @Column(name = "IS_INTERNAL")
    private boolean internal;

    // Status: OPEN, COMPLETED, CANCELLED
    private String status;

    /**
     * Relație OneToMany către aplicații.
     * Folosim 'mappedBy' pentru a indica faptul că Application este proprietarul relației.
     * LAZY este setat implicit la OneToMany, dar este bine să fie vizibil.
     */
    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Application> applications = new ArrayList<>();

    public Competition() {
        // Constructor gol cerut de specificația JPA
    }

    // --- METODE HELPER (Bune practici pentru relații bidirecționale) ---

    /**
     * Adaugă o aplicație și menține sincronizarea între cele două obiecte.
     */
    public void addApplication(Application application) {
        applications.add(application);
        application.setCompetition(this);
    }

    /**
     * Scoate o aplicație și rupe legătura dintre obiecte.
     */
    public void removeApplication(Application application) {
        applications.remove(application);
        application.setCompetition(null);
    }

    // --- GETTERS & SETTERS (Organizați pentru lizibilitate) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getMinParticipants() { return minParticipants; }
    public void setMinParticipants(Integer minParticipants) { this.minParticipants = minParticipants; }

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public LocalDateTime getApplicationStart() { return applicationStart; }
    public void setApplicationStart(LocalDateTime applicationStart) { this.applicationStart = applicationStart; }

    public LocalDateTime getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDateTime applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public boolean isInternal() { return internal; }
    public void setInternal(boolean internal) { this.internal = internal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }
}