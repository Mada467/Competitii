package competitii.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "APPLICATIONS")
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER) // Eager pentru a avea datele studentului imediat
    @JoinColumn(name = "USER_ID", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy pentru performanță
    @JoinColumn(name = "COMPETITION_ID", nullable = false)
    private Competition competition;

    @Column(nullable = false)
    private String status;      // "PENDING", "ACCEPTED", "REJECTED"

    private Double score;

    @Column(name = "APPLY_DATE")
    private LocalDateTime applyDate;

    @Column(length = 1000)
    private String additionalInfo;

    public Application() {
        this.applyDate = LocalDateTime.now(); // Setăm data automat la creare
    }

    // Getters și Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public Competition getCompetition() { return competition; }
    public void setCompetition(Competition competition) { this.competition = competition; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public LocalDateTime getApplyDate() { return applyDate; }
    public void setApplyDate(LocalDateTime applyDate) { this.applyDate = applyDate; }

    public String getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }
}