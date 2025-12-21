package competitii.beans;

import competitii.entities.Application;
import competitii.entities.Competition;
import competitii.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Bean pentru gestionarea logicii de business a competițiilor.
 * Gestionează CRUD pe competiții și ciclul de viață al înscrierilor.
 */
@Stateless
public class CompetitionsBean {

    private static final Logger LOGGER = Logger.getLogger(CompetitionsBean.class.getName());

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    // --- LOGICĂ COMPETIȚII ---

    public List<Competition> getAllCompetitions() {
        return entityManager.createQuery(
                "SELECT c FROM Competition c ORDER BY c.applicationDeadline ASC",
                Competition.class).getResultList();
    }

    public Competition findById(Long id) {
        return entityManager.find(Competition.class, id);
    }

    @Transactional
    public void createCompetition(String name, String description, Integer minP, Integer maxP,
                                  LocalDateTime start, LocalDateTime deadline, boolean isInternal) {
        Competition competition = new Competition();
        updateCompetitionFields(competition, name, description, minP, maxP, start, deadline, isInternal);
        competition.setStatus("OPEN");
        entityManager.persist(competition);
    }

    @Transactional
    public void updateCompetition(Long id, String name, String description, Integer minP, Integer maxP,
                                  LocalDateTime start, LocalDateTime deadline, boolean isInternal, String status) {
        Competition competition = findById(id);
        if (competition != null) {
            updateCompetitionFields(competition, name, description, minP, maxP, start, deadline, isInternal);
            if (status != null) {
                competition.setStatus(status);
            }
            entityManager.merge(competition);
        }
    }

    private void updateCompetitionFields(Competition comp, String name, String description, Integer minP,
                                         Integer maxP, LocalDateTime start, LocalDateTime deadline, boolean isInternal) {
        comp.setName(name);
        comp.setDescription(description);
        comp.setMinParticipants(minP);
        comp.setMaxParticipants(maxP);
        comp.setApplicationStart(start);
        comp.setApplicationDeadline(deadline);
        comp.setInternal(isInternal);
    }

    @Transactional
    public void deleteCompetitions(List<Long> competitionIds) {
        for (Long id : competitionIds) {
            Competition competition = findById(id);
            if (competition != null) {
                entityManager.createQuery("DELETE FROM Application a WHERE a.competition.id = :compId")
                        .setParameter("compId", id)
                        .executeUpdate();
                entityManager.remove(competition);
            }
        }
    }

    // --- LOGICĂ ÎNSCRIERI (APPLICATIONS) ---

    @Transactional
    public String applyToCompetition(Long userId, Long competitionId, String additionalInfo) {
        User student = entityManager.find(User.class, userId);
        Competition comp = findById(competitionId);

        if (comp == null || student == null) return "Eroare: Date inexistente.";

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(comp.getApplicationStart())) return "Înscrierile nu au început!";
        if (now.isAfter(comp.getApplicationDeadline())) return "Termenul limită a expirat!";

        // Verificare email student UPT
        if (comp.isInternal() && (student.getEmail() == null || !student.getEmail().endsWith("@student.upt.ro"))) {
            return "Necesită email de student UPT (@student.upt.ro)!";
        }

        if (hasStudentApplied(userId, competitionId)) return "Ai aplicat deja!";

        if (comp.getMaxParticipants() != null) {
            long currentApps = getApplicationsCount(competitionId);
            if (currentApps >= comp.getMaxParticipants()) return "Capacitate maximă atinsă!";
        }

        Application app = new Application();
        app.setStudent(student);
        app.setCompetition(comp);
        app.setStatus("PENDING");
        app.setAdditionalInfo(additionalInfo);
        app.setApplyDate(now);

        entityManager.persist(app);
        return "SUCCESS";
    }

    /**
     * METODA NOUĂ: Rezolvă eroarea din Servlet-ul WithdrawFromCompetition
     */
    @Transactional
    public void withdrawFromCompetition(Long userId, Long competitionId) {
        try {
            entityManager.createQuery(
                            "DELETE FROM Application a WHERE a.student.id = :uId AND a.competition.id = :cId")
                    .setParameter("uId", userId)
                    .setParameter("cId", competitionId)
                    .executeUpdate();
            LOGGER.log(Level.INFO, "Studentul {0} s-a retras din competiția {1}", new Object[]{userId, competitionId});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la retragerea din competiție", e);
            throw e;
        }
    }

    @Transactional
    public boolean approveApplication(Long applicationId) {
        return updateApplicationStatus(applicationId, "ACCEPTED");
    }

    @Transactional
    public boolean rejectApplication(Long applicationId) {
        return updateApplicationStatus(applicationId, "REJECTED");
    }

    private boolean updateApplicationStatus(Long appId, String newStatus) {
        Application app = entityManager.find(Application.class, appId);
        if (app != null && "PENDING".equals(app.getStatus())) {
            app.setStatus(newStatus);
            entityManager.merge(app);
            return true;
        }
        return false;
    }

    public List<Application> getApplicationsForCompetition(Long competitionId) {
        return entityManager.createQuery(
                        "SELECT a FROM Application a JOIN FETCH a.student WHERE a.competition.id = :compId",
                        Application.class)
                .setParameter("compId", competitionId)
                .getResultList();
    }

    public boolean hasStudentApplied(Long userId, Long competitionId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(a) FROM Application a WHERE a.student.id = :uId AND a.competition.id = :cId", Long.class)
                .setParameter("uId", userId)
                .setParameter("cId", competitionId)
                .getSingleResult();
        return count > 0;
    }

    private long getApplicationsCount(Long competitionId) {
        return (long) entityManager.createQuery("SELECT COUNT(a) FROM Application a WHERE a.competition.id = :compId")
                .setParameter("compId", competitionId)
                .getSingleResult();
    }
}