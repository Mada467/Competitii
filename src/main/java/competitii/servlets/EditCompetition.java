package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.Competition;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Servlet pentru editarea unei competiții existente.
 * Gestionează atât pre-încărcarea datelor în formular, cât și salvarea modificărilor.
 */
@WebServlet(name = "EditCompetition", value = "/EditCompetition")
public class EditCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    // Standard ISO pentru input type="datetime-local" din HTML5
    private static final DateTimeFormatter HTML5_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces interzis!");
            return;
        }

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Competition comp = competitionsBean.findById(id);

            if (comp == null) {
                response.sendRedirect(request.getContextPath() + "/Competitions?error=notFound");
                return;
            }

            // Pregătim datele pentru a fi afișate corect în input-urile de tip calendar
            prepareDateFields(request, comp);

            request.setAttribute("competition", comp);
            request.getRequestDispatcher("/WEB-INF/pages/editCompetition.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidId");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAuthorized(request)) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            // Preluăm noile valori din formular
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int minP = Integer.parseInt(request.getParameter("min_participants"));
            int maxP = Integer.parseInt(request.getParameter("max_participants"));

            LocalDateTime start = LocalDateTime.parse(request.getParameter("start_date"));
            LocalDateTime deadline = LocalDateTime.parse(request.getParameter("deadline"));

            boolean isInternal = request.getParameter("is_internal") != null;
            String status = request.getParameter("status");

            // Apelăm update-ul în Bean
            competitionsBean.updateCompetition(id, name, description, minP, maxP, start, deadline, isInternal, status);

            response.sendRedirect(request.getContextPath() + "/Competitions?updated=true");

        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=updateFailed");
        }
    }

    /**
     * Formatează datele competiției pentru a fi compatibile cu input-urile HTML5.
     */
    private void prepareDateFields(HttpServletRequest request, Competition comp) {
        String start = (comp.getApplicationStart() != null) ? comp.getApplicationStart().format(HTML5_FORMATTER) : "";
        String deadline = (comp.getApplicationDeadline() != null) ? comp.getApplicationDeadline().format(HTML5_FORMATTER) : "";

        request.setAttribute("formattedStart", start);
        request.setAttribute("formattedDeadline", deadline);
    }

    /**
     * Verifică dacă utilizatorul este logat și are rol de reprezentant.
     */
    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;
        User user = (User) session.getAttribute("user");
        return user != null && "REPRESENTATIVE".equals(user.getRole());
    }
}