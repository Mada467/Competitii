package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ApplyToCompetition", value = "/ApplyToCompetition")
public class ApplyToCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        // PERMITEM ȘI PENTRU ELEV
        String role = user.getRole();
        if (!"STUDENT".equals(role) && !"ELEV".equals(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Doar studenții și elevii pot aplica.");
            return;
        }

        try {
            Long compId = Long.parseLong(request.getParameter("competition_id"));
            String additionalInfo = request.getParameter("additional_info");

            String result = competitionsBean.applyToCompetition(user.getId(), compId, additionalInfo);

            if ("SUCCESS".equals(result)) {
                response.sendRedirect(request.getContextPath() + "/Competitions?applied=true");
            } else {
                response.sendRedirect(request.getContextPath() + "/Competitions?error=" + java.net.URLEncoder.encode(result, "UTF-8"));
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=true");
        }
    }
}