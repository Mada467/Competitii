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

@Stateless
public class CompetitionsBean {

    private static final Logger LOGGER = Logger.getLogger(CompetitionsBean.class.getName());

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public List<Competition> getAllCompetitions() {
        return entityManager.createQuery(
                "SELECT c FROM Competition c ORDER BY c.applicationDeadline ASC",
                Competition.class).getResultList();
    }

    public void createCompetition(String name, String description, Integer minP, Integer maxP,
                                  LocalDateTime start, LocalDateTime deadline, boolean isInternal) {
        Competition competition = new Competition();
        competition.setName(name);
        competition.setDescription(description);
        competition.setMinParticipants(minP);
        competition.setMaxParticipants(maxP);
        competition.setApplicationStart(start);
        competition.setApplicationDeadline(deadline);
        competition.setInternal(isInternal);
        competition.setStatus("OPEN");
        entityManager.persist(competition);
        entityManager.flush();
    }

    public Competition findById(Long id) {
        return entityManager.find(Competition.class, id);
    }

    public void updateCompetition(Long id, String name, String description, Integer minP, Integer maxP,
                                  LocalDateTime start, LocalDateTime deadline, boolean isInternal, String status) {
        Competition competition = entityManager.find(Competition.class, id);
        if (competition != null) {
            competition.setName(name);
            competition.setDescription(description);
            competition.setMinParticipants(minP);
            competition.setMaxParticipants(maxP);
            competition.setApplicationStart(start);
            competition.setApplicationDeadline(deadline);
            competition.setInternal(isInternal);
            competition.setStatus(status != null ? status : competition.getStatus());
            entityManager.merge(competition);
            entityManager.flush();
        }
    }

    public String applyToCompetition(Long userId, Long competitionId, String additionalInfo) {
        User student = entityManager.find(User.class, userId);
        Competition comp = entityManager.find(Competition.class, competitionId);

        if (comp == null || student == null) return "Utilizator sau Competiție inexistentă!";

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(comp.getApplicationStart())) return "Înscrierile nu au început încă!";
        if (now.isAfter(comp.getApplicationDeadline())) return "Termenul limită a expirat!";

        if (comp.isInternal() && (student.getEmail() == null || !student.getEmail().endsWith("@student.upt.ro"))) {
            return "Acces refuzat: Necesită email @student.upt.ro!";
        }

        if (hasStudentApplied(userId, competitionId)) return "Ai aplicat deja la această competiție!";

        if (comp.getMaxParticipants() != null) {
            long currentApps = (long) getApplicationsForCompetition(competitionId).size();
            if (currentApps >= comp.getMaxParticipants()) return "Capacitate maximă atinsă!";
        }

        Application app = new Application();
        app.setStudent(student);
        app.setCompetition(comp);
        app.setStatus("PENDING");
        app.setAdditionalInfo(additionalInfo);
        app.setApplyDate(now);

        entityManager.persist(app);
        entityManager.flush();
        return "SUCCESS";
    }

    public void markAsComplete(Long id) {
        Competition comp = entityManager.find(Competition.class, id);
        if (comp != null) {
            comp.setStatus("COMPLETED");
            entityManager.merge(comp);
            entityManager.flush();
        }
    }

    public List<Application> getApplicationsForCompetition(Long competitionId) {
        return entityManager.createQuery(
                        "SELECT a FROM Application a JOIN FETCH a.student WHERE a.competition.id = :compId",
                        Application.class)
                .setParameter("compId", competitionId)
                .getResultList();
    }

    @Transactional
    public void deleteCompetitions(List<Long> competitionIds) {
        for (Long id : competitionIds) {
            try {
                Competition competition = entityManager.find(Competition.class, id);
                if (competition != null) {
                    entityManager.createQuery("DELETE FROM Application a WHERE a.competition.id = :compId")
                            .setParameter("compId", id)
                            .executeUpdate();
                    entityManager.flush();
                    entityManager.remove(competition);
                    entityManager.flush();
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Eroare la ștergerea competiției cu ID " + id, e);
                throw e;
            }
        }
    }

    public boolean approveApplication(Long applicationId) {
        try {
            Application app = entityManager.find(Application.class, applicationId);
            if (app != null && "PENDING".equals(app.getStatus())) {
                app.setStatus("ACCEPTED");
                entityManager.merge(app);
                entityManager.flush();
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la aprobarea aplicației " + applicationId, e);
            return false;
        }
        return false;
    }

    // NOU: Metoda pentru RESPINGEREA aplicației
    public boolean rejectApplication(Long applicationId) {
        try {
            Application app = entityManager.find(Application.class, applicationId);
            if (app != null && "PENDING".equals(app.getStatus())) {
                app.setStatus("REJECTED");
                entityManager.merge(app);
                entityManager.flush();
                return true;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la respingerea aplicației " + applicationId, e);
            return false;
        }
        return false;
    }

    @Transactional
    public void withdrawFromCompetition(Long userId, Long competitionId) {
        try {
            entityManager.createQuery("DELETE FROM Application a WHERE a.student.id = :uId AND a.competition.id = :cId")
                    .setParameter("uId", userId)
                    .setParameter("cId", competitionId)
                    .executeUpdate();
            entityManager.flush();
            LOGGER.log(Level.INFO, "Studentul {0} s-a retras din competiția {1}", new Object[]{userId, competitionId});
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la retragerea din competiție", e);
        }
    }

    public boolean hasStudentApplied(Long userId, Long competitionId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(a) FROM Application a WHERE a.student.id = :uId AND a.competition.id = :cId", Long.class)
                .setParameter("uId", userId)
                .setParameter("cId", competitionId)
                .getSingleResult();
        return count > 0;
    }
}