package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet responsabil pentru respingerea unei înscrieri.
 * Modifică statusul aplicației din PENDING în REJECTED.
 */
@WebServlet(name = "RejectStudent", value = "/RejectStudent")
public class RejectStudent extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificare Securitate (doar reprezentantul poate respinge)
        if (!isAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces refuzat.");
            return;
        }

        try {
            // 2. Extragem și validăm parametrii
            String appIdParam = request.getParameter("application_id");
            String compIdParam = request.getParameter("competition_id");

            if (appIdParam == null || compIdParam == null) {
                response.sendRedirect(request.getContextPath() + "/Competitions");
                return;
            }

            Long appId = Long.parseLong(appIdParam);
            Long compId = Long.parseLong(compIdParam);

            // 3. Executăm respingerea în baza de date
            boolean isRejected = competitionsBean.rejectApplication(appId);

            // 4. Redirecționăm înapoi la lista de aplicații a competiției respective
            String redirectUrl = request.getContextPath() + "/ViewApplications?id=" + compId;
            if (isRejected) {
                response.sendRedirect(redirectUrl + "&rejected=true");
            } else {
                response.sendRedirect(redirectUrl + "&error=rejectionFailed");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidFormat");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=serverError");
        }
    }

    /**
     * Verifică dacă acțiunea este efectuată de un REPRESENTATIVE logat.
     */
    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        User user = (User) session.getAttribute("user");
        return user != null && "REPRESENTATIVE".equals(user.getRole());
    }
}