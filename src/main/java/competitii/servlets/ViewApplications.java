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

/**
 * Servlet pentru vizualizarea detaliată a unei competiții.
 * Calculează statistici pentru bara de progres și lista de participanți.
 */
@WebServlet(name = "ViewApplications", value = "/ViewApplications")
public class ViewApplications extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // 1. Validare parametru ID
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/Competitions");
            return;
        }

        try {
            Long competitionId = Long.parseLong(idParam);
            Competition competition = competitionsBean.findById(competitionId);

            // Verificăm dacă competiția chiar există în DB
            if (competition == null) {
                response.sendRedirect(request.getContextPath() + "/Competitions?error=notFound");
                return;
            }

            List<Application> applications = competitionsBean.getApplicationsForCompetition(competitionId);

            // 2. Calculăm statisticile pentru Dashboard (Progress Bar)
            long totalInscrisi = applications.size();
            long aprobati = countByStatus(applications, "ACCEPTED");
            long respinsi = countByStatus(applications, "REJECTED");
            long inAsteptare = totalInscrisi - aprobati - respinsi;

            // 3. Calculăm procentul de ocupare
            int procentOcupare = calculateOccupancy(totalInscrisi, competition.getMaxParticipants());

            // 4. Setăm atributele pentru JSP
            request.setAttribute("competition", competition);
            request.setAttribute("applications", applications);
            request.setAttribute("total", totalInscrisi);
            request.setAttribute("aprobati", aprobati);
            request.setAttribute("asteptare", inAsteptare);
            request.setAttribute("procent", Math.min(procentOcupare, 100)); // Nu depășim 100% vizual

            request.getRequestDispatcher("/WEB-INF/pages/viewApplications.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidId");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=generic");
        }
    }

    // --- METODE HELPER (Simplifică logica din doGet) ---

    private long countByStatus(List<Application> apps, String status) {
        return apps.stream()
                .filter(a -> status.equals(a.getStatus()))
                .count();
    }

    private int calculateOccupancy(long current, Integer max) {
        if (max == null || max <= 0) return 0;
        return (int) ((current * 100) / max);
    }
}