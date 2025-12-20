package competitii.beans;

import competitii.entities.Competition;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CompetitionsBean {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    public List<Competition> getAllCompetitions() {
        return entityManager.createQuery("SELECT c FROM Competition c", Competition.class).getResultList();
    }

    public void createCompetition(String name, String description, Integer minParticipants, Integer maxParticipants) {
        Competition competition = new Competition();
        competition.setName(name);
        competition.setDescription(description);
        competition.setMinParticipants(minParticipants);
        competition.setMaxParticipants(maxParticipants);
        entityManager.persist(competition);
    }

    // Găsește o competiție după ID pentru a o edita
    public Competition findById(Long id) {
        return entityManager.find(Competition.class, id);
    }

    // Actualizează datele în baza de date
    public void updateCompetition(Long id, String name, String description, Integer minP, Integer maxP) {
        Competition competition = entityManager.find(Competition.class, id);
        if (competition != null) {
            competition.setName(name);
            competition.setDescription(description);
            competition.setMinParticipants(minP);
            competition.setMaxParticipants(maxP);
        }
    }

    public void deleteCompetitions(List<Long> competitionIds) {
        for (Long id : competitionIds) {
            Competition competition = entityManager.find(Competition.class, id);
            if (competition != null) {
                entityManager.remove(competition);
            }
        }
    }
}