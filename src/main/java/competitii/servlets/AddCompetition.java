package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "AddCompetition", value = "/AddCompetition")
public class AddCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Cerința: Doar Managerul/Reprezentantul departamentului poate crea
        if (user == null || !"REPRESENTATIVE".equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces interzis. Doar reprezentanții pot crea competiții.");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/addCompetition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null || !"REPRESENTATIVE".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            int minP = Integer.parseInt(request.getParameter("min_participants"));
            int maxP = Integer.parseInt(request.getParameter("max_participants"));

            // Preluare și parsare date calendaristice (format: yyyy-MM-ddTHH:mm)
            LocalDateTime start = LocalDateTime.parse(request.getParameter("start_date"));
            LocalDateTime deadline = LocalDateTime.parse(request.getParameter("deadline"));

            // Checkbox: isInternal (Cerința: institutional address check)
            boolean isInternal = request.getParameter("is_internal") != null;

            // Apelăm metoda actualizată din Bean
            competitionsBean.createCompetition(name, description, minP, maxP, start, deadline, isInternal);

            response.sendRedirect(request.getContextPath() + "/Competitions");
        } catch (Exception e) {
            request.setAttribute("error", "Date invalide! Asigurați-vă că toate câmpurile sunt completate corect.");
            request.getRequestDispatcher("/WEB-INF/pages/addCompetition.jsp").forward(request, response);
        }
    }
}