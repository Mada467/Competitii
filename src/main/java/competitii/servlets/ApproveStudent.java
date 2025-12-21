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

@WebServlet(name = "ApproveStudent", value = "/ApproveStudent")
public class ApproveStudent extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ApproveStudent.class.getName());

    @Inject
    private CompetitionsBean competitionsBean;

    // Folosim doPost deoarece formularul din JSP este de tip POST pentru securitate
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Verificare securitate: Doar REPRESENTATIVE poate aproba
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"REPRESENTATIVE".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Doar reprezentanții pot aproba participanți!");
            return;
        }

        try {
            // 2. Preluăm parametrii (Am potrivit numele cu cele din formularul JSP: application_id și competition_id)
            String appIdStr = request.getParameter("application_id");
            String compIdStr = request.getParameter("competition_id");

            if (appIdStr == null || compIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/Competitions?error=missingData");
                return;
            }

            Long appId = Long.parseLong(appIdStr);
            Long compId = Long.parseLong(compIdStr);

            // 3. Aprobăm aplicația folosind metoda din Bean
            boolean success = competitionsBean.approveApplication(appId);

            // 4. Redirecționăm înapoi la listă cu mesaj de succes sau eroare
            if (success) {
                response.sendRedirect(request.getContextPath() + "/ViewApplications?id=" + compId + "&approved=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/ViewApplications?id=" + compId + "&error=failed");
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Eroare la aprobare", e);
            response.sendRedirect(request.getContextPath() + "/Competitions?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Dacă cineva încearcă să acceseze direct URL-ul, îl trimitem la listă
        response.sendRedirect(request.getContextPath() + "/Competitions");
    }
}