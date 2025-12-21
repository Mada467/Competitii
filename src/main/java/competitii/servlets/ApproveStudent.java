package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet responsabil pentru aprobarea unei înscrieri.
 * Această acțiune modifică statusul aplicației din PENDING în ACCEPTED.
 */
@WebServlet(name = "ApproveStudent", value = "/ApproveStudent")
public class ApproveStudent extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ApproveStudent.class.getName());

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificăm dacă utilizatorul are dreptul de a efectua această acțiune
        if (!isAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces neautorizat.");
            return;
        }

        try {
            // 2. Extragem ID-urile necesare pentru procesare
            String appIdParam = request.getParameter("application_id");
            String compIdParam = request.getParameter("competition_id");

            // Validare de bază pentru parametri (prevenim NullPointerException)
            if (appIdParam == null || compIdParam == null) {
                response.sendRedirect(request.getContextPath() + "/Competitions");
                return;
            }

            Long appId = Long.parseLong(appIdParam);
            Long compId = Long.parseLong(compIdParam);

            // 3. Executăm logica de business
            boolean isApproved = competitionsBean.approveApplication(appId);

            // 4. Redirecționăm înapoi la pagina de detalii a competiției cu un indicator de status
            String redirectUrl = request.getContextPath() + "/ViewApplications?id=" + compId;
            if (isApproved) {
                response.sendRedirect(redirectUrl + "&approved=true");
            } else {
                response.sendRedirect(redirectUrl + "&error=approvalFailed");
            }

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Format ID invalid primit în ApproveStudent");
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidFormat");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare neprevăzută la aprobarea studentului", e);
            response.sendRedirect(request.getContextPath() + "/Competitions?error=serverError");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Acțiunile de modificare (aprobare/respingere) nu se fac prin GET
        response.sendRedirect(request.getContextPath() + "/Competitions");
    }

    /**
     * Helper pentru verificarea rolului de reprezentant.
     */
    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        User user = (User) session.getAttribute("user");
        return user != null && "REPRESENTATIVE".equals(user.getRole());
    }
}