    package competitii.entities;

    import jakarta.persistence.*;
    import java.io.Serializable;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.List;

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

        private Integer minParticipants;
        private Integer maxParticipants;

        private LocalDateTime applicationStart;
        private LocalDateTime applicationDeadline;

        private boolean isInternal;

        // Status: OPEN, COMPLETED, CANCELLED
        private String status;

        // RELAȚIE NOUĂ: Permite accesul la lista de studenți înscriși
        // orphanRemoval = true șterge aplicațiile din DB dacă sunt scoase din această listă
        @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
        private List<Application> applications = new ArrayList<>();

        public Competition() {}

        // Getters și Setters existente
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
        public void setApplicationStart(LocalDateTime start) { this.applicationStart = start; }
        public LocalDateTime getApplicationDeadline() { return applicationDeadline; }
        public void setApplicationDeadline(LocalDateTime d) { this.applicationDeadline = d; }
        public boolean isInternal() { return isInternal; }
        public void setInternal(boolean internal) { isInternal = internal; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        // Getter și Setter pentru lista de aplicații
        public List<Application> getApplications() { return applications; }
        public void setApplications(List<Application> applications) { this.applications = applications; }
    }