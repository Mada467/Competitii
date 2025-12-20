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
}