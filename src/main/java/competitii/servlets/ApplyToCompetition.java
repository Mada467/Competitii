package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Servlet care gestionează procesul de înscriere a unui student/elev la o competiție.
 */
@WebServlet(name = "ApplyToCompetition", value = "/ApplyToCompetition")
public class ApplyToCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // 1. Verificare Autentificare
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // 2. Verificare Rol (Permitem atât STUDENT cât și ELEV conform cerinței tale)
        if (!isEligibleRole(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Doar studenții și elevii pot aplica la competiții.");
            return;
        }

        try {
            // 3. Preluare parametri din formular
            Long compId = Long.parseLong(request.getParameter("competition_id"));
            String additionalInfo = request.getParameter("additional_info");

            // 4. Apelare logică de business din Bean
            String result = competitionsBean.applyToCompetition(user.getId(), compId, additionalInfo);

            // 5. Tratare rezultat și redirecționare
            if ("SUCCESS".equals(result)) {
                response.sendRedirect(request.getContextPath() + "/Competitions?applied=true");
            } else {
                // Dacă avem un mesaj de eroare specific (ex: "Ai aplicat deja"), îl trimitem codificat în URL
                String encodedMessage = URLEncoder.encode(result, StandardCharsets.UTF_8);
                response.sendRedirect(request.getContextPath() + "/Competitions?error=" + encodedMessage);
            }

        } catch (Exception e) {
            // Eroare neprevăzută (ex: format ID invalid)
            response.sendRedirect(request.getContextPath() + "/Competitions?error=generic");
        }
    }

    /**
     * Verifică dacă utilizatorul are un rol care îi permite înscrierea.
     */
    private boolean isEligibleRole(User user) {
        String role = user.getRole();
        return "STUDENT".equals(role) || "ELEV".equals(role);
    }
}