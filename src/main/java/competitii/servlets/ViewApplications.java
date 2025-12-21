package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.Application;
import competitii.entities.Competition;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ViewApplications", value = "/ViewApplications")
public class ViewApplications extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/Competitions");
                return;
            }

            Long competitionId = Long.parseLong(idParam);
            Competition competition = competitionsBean.findById(competitionId);
            List<Application> applications = competitionsBean.getApplicationsForCompetition(competitionId);

            // --- CALCULE STATISTICE ---
            long totalInscrisi = applications.size();
            long aprobati = applications.stream()
                    .filter(a -> "ACCEPTED".equals(a.getStatus()))
                    .count();
            long inAsteptare = totalInscrisi - aprobati;

            // Calculăm procentul de ocupare (câți s-au înscris față de capacitatea maximă)
            int procentOcupare = 0;
            if (competition.getMaxParticipants() != null && competition.getMaxParticipants() > 0) {
                procentOcupare = (int) ((totalInscrisi * 100) / competition.getMaxParticipants());
            }

            // Trimitem toate datele către JSP
            request.setAttribute("competition", competition);
            request.setAttribute("applications", applications);
            request.setAttribute("total", totalInscrisi);
            request.setAttribute("aprobati", aprobati);
            request.setAttribute("asteptare", inAsteptare);
            request.setAttribute("procent", procentOcupare);

            request.getRequestDispatcher("/WEB-INF/pages/viewApplications.jsp").forward(request, response);

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=true");
        }
    }
}