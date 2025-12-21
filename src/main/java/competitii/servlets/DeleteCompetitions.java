package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "DeleteCompetitions", value = "/DeleteCompetitions")
public class DeleteCompetitions extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteCompetitions.class.getName());

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificăm securitatea (doar reprezentantul poate șterge)
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"REPRESENTATIVE".equals(user.getRole())) {
            LOGGER.log(Level.WARNING, "Tentativă de ștergere neautorizată de la: {0}",
                    user != null ? user.getUsername() : "unknown");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces refuzat! Doar reprezentanții pot șterge competiții.");
            return;
        }

        // 2. Preluăm array-ul de ID-uri din checkbox-uri
        String[] competitionIdsAsString = request.getParameterValues("competition_ids");

        if (competitionIdsAsString == null || competitionIdsAsString.length == 0) {
            LOGGER.log(Level.INFO, "Nicio competiție selectată pentru ștergere");
            response.sendRedirect(request.getContextPath() + "/Competitions?message=noSelection");
            return;
        }

        try {
            List<Long> ids = new ArrayList<>();
            for (String idStr : competitionIdsAsString) {
                ids.add(Long.parseLong(idStr));
            }

            LOGGER.log(Level.INFO, "Ștergere competiții: {0} de către {1}",
                    new Object[]{ids, user.getUsername()});

            // 3. Apelăm ștergerea în Bean (JTA transaction managed)
            competitionsBean.deleteCompetitions(ids);

            LOGGER.log(Level.INFO, "Ștergere reușită pentru {0} competiții", ids.size());

            // 4. Redirecționăm cu mesaj de succes
            response.sendRedirect(request.getContextPath() + "/Competitions?deleted=success");

        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "ID-uri invalide pentru ștergere", e);
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidIds");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la ștergerea competițiilor", e);
            response.sendRedirect(request.getContextPath() + "/Competitions?error=deleteFailed");
        }
    }
}