package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Servlet responsabil pentru ștergerea în masă (bulk delete) a competițiilor.
 * Procesul include și ștergerea în cascadă a înscrierilor asociate.
 */
@WebServlet(name = "DeleteCompetitions", value = "/DeleteCompetitions")
public class DeleteCompetitions extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DeleteCompetitions.class.getName());

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificăm securitatea folosind metoda helper pentru consistență
        if (!isAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces interzis!");
            return;
        }

        // 2. Preluăm ID-urile din checkbox-uri
        String[] idsArray = request.getParameterValues("competition_ids");

        if (idsArray == null || idsArray.length == 0) {
            response.sendRedirect(request.getContextPath() + "/Competitions?message=noSelection");
            return;
        }

        try {
            // 3. Conversie modernă din String[] în List<Long> folosind Streams
            List<Long> idsToClear = Arrays.stream(idsArray)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            // Logăm acțiunea pentru audit (important în aplicații de business)
            User user = (User) request.getSession().getAttribute("user");
            LOGGER.log(Level.INFO, "Utilizatorul {0} șterge competițiile: {1}",
                    new Object[]{user.getUsername(), idsToClear});

            // 4. Executăm ștergerea
            competitionsBean.deleteCompetitions(idsToClear);

            response.sendRedirect(request.getContextPath() + "/Competitions?deleted=true");

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Format ID invalid la ștergere");
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidFormat");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare critică la ștergere", e);
            response.sendRedirect(request.getContextPath() + "/Competitions?error=serverError");
        }
    }

    /**
     * Verifică dacă utilizatorul curent are dreptul de a șterge resurse.
     */
    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        User user = (User) session.getAttribute("user");
        return user != null && "REPRESENTATIVE".equals(user.getRole());
    }
}