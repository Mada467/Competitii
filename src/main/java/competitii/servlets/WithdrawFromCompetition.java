package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet responsabil pentru retragerea unui student/elev dintr-o competiție.
 * Șterge aplicația existentă din sistem.
 */
@WebServlet(name = "WithdrawFromCompetition", value = "/WithdrawFromCompetition")
public class WithdrawFromCompetition extends HttpServlet {

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

        // 2. Verificare Rol (Permitem STUDENT și ELEV)
        if (!isEligibleForWithdraw(user)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Doar studenții și elevii își pot retrage aplicația.");
            return;
        }

        try {
            // 3. Preluare și validare ID competiție
            String compIdParam = request.getParameter("competition_id");
            if (compIdParam == null || compIdParam.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/Competitions");
                return;
            }

            Long competitionId = Long.parseLong(compIdParam);

            // 4. Executăm logica de business prin Bean
            competitionsBean.withdrawFromCompetition(user.getId(), competitionId);

            // 5. Redirecționare cu feedback de succes
            response.sendRedirect(request.getContextPath() + "/Competitions?withdrawn=true");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidId");
        } catch (Exception e) {
            // Logăm eroarea internă dacă este cazul
            response.sendRedirect(request.getContextPath() + "/Competitions?error=withdrawFailed");
        }
    }

    /**
     * Verifică dacă utilizatorul are un rol eligibil pentru această operațiune.
     */
    private boolean isEligibleForWithdraw(User user) {
        String role = user.getRole();
        return "STUDENT".equals(role) || "ELEV".equals(role);
    }
}