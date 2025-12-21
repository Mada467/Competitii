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

@WebServlet(name = "EditCompetition", value = "/EditCompetition")
public class EditCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    // Formatter pentru datetime-local input (yyyy-MM-ddTHH:mm)
    private static final DateTimeFormatter HTML5_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificăm dacă utilizatorul este Reprezentant
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"REPRESENTATIVE".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Doar reprezentanții pot edita competiții!");
            return;
        }

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            Competition comp = competitionsBean.findById(id);

            if (comp != null) {
                // Formatăm datele pentru input type="datetime-local"
                String formattedStart = comp.getApplicationStart() != null
                        ? comp.getApplicationStart().format(HTML5_FORMATTER)
                        : "";
                String formattedDeadline = comp.getApplicationDeadline() != null
                        ? comp.getApplicationDeadline().format(HTML5_FORMATTER)
                        : "";

                request.setAttribute("competition", comp);
                request.setAttribute("formattedStart", formattedStart);
                request.setAttribute("formattedDeadline", formattedDeadline);

                request.getRequestDispatcher("/WEB-INF/pages/editCompetition.jsp")
                        .forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/Competitions?error=notFound");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=invalidId");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Verificare securitate
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"REPRESENTATIVE".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int minP = Integer.parseInt(request.getParameter("min_participants"));
            int maxP = Integer.parseInt(request.getParameter("max_participants"));

            // Parsarea datelor din format datetime-local
            LocalDateTime start = LocalDateTime.parse(request.getParameter("start_date"));
            LocalDateTime deadline = LocalDateTime.parse(request.getParameter("deadline"));

            boolean isInternal = request.getParameter("is_internal") != null;
            String status = request.getParameter("status");

            // Metodă de update în Bean
            competitionsBean.updateCompetition(id, name, description, minP, maxP,
                    start, deadline, isInternal, status);

            response.sendRedirect(request.getContextPath() + "/Competitions?updated=success");
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=updateFailed");
        }
    }
}