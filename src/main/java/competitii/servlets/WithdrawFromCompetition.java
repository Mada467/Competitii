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

@WebServlet(name = "WithdrawFromCompetition", value = "/WithdrawFromCompetition")
public class WithdrawFromCompetition extends HttpServlet {

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
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces interzis.");
            return;
        }

        try {
            Long competitionId = Long.parseLong(request.getParameter("competition_id"));
            competitionsBean.withdrawFromCompetition(user.getId(), competitionId);
            response.sendRedirect(request.getContextPath() + "/Competitions?withdrawn=true");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=true");
        }
    }
}