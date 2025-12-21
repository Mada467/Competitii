package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Servlet responsabil pentru crearea unei noi competiții.
 * Accesul este restricționat strict pentru utilizatorii cu rolul REPRESENTATIVE.
 */
@WebServlet(name = "AddCompetition", value = "/AddCompetition")
public class AddCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificăm dacă utilizatorul are dreptul de a accesa pagina de creare
        if (!isAuthorized(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acces interzis!");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/addCompetition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAuthorized(request)) {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        try {
            // Extragem datele din formular
            String name = request.getParameter("name");
            String description = request.getParameter("description");

            // Parsăm valorile numerice și datele calendaristice
            int minP = Integer.parseInt(request.getParameter("min_participants"));
            int maxP = Integer.parseInt(request.getParameter("max_participants"));

            // Formatul implicit pentru <input type="datetime-local"> este ISO (yyyy-MM-ddTHH:mm)
            LocalDateTime start = LocalDateTime.parse(request.getParameter("start_date"));
            LocalDateTime deadline = LocalDateTime.parse(request.getParameter("deadline"));

            // Checkbox-ul trimite valoare doar dacă este bifat
            boolean isInternal = request.getParameter("is_internal") != null;

            // Validare simplă la nivel de business: deadline-ul trebuie să fie după start
            if (deadline.isBefore(start)) {
                throw new IllegalArgumentException("Termenul limită nu poate fi înainte de data de start!");
            }

            competitionsBean.createCompetition(name, description, minP, maxP, start, deadline, isInternal);

            // Succes: mergem la lista de competiții
            response.sendRedirect(request.getContextPath() + "/Competitions");

        } catch (Exception e) {
            // Logăm eroarea și trimitem un mesaj util către UI
            request.setAttribute("error", "Eroare: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/addCompetition.jsp").forward(request, response);
        }
    }

    /**
     * Metodă privată pentru centralizarea verificării de rol.
     * Ajută la menținerea codului curat în doGet și doPost.
     */
    private boolean isAuthorized(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return false;

        User user = (User) session.getAttribute("user");
        return user != null && "REPRESENTATIVE".equals(user.getRole());
    }
}