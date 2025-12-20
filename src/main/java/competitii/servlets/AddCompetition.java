package competitii.servlets;

import competitii.beans.CompetitionsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AddCompetition", value = "/AddCompetition")
public class AddCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/addCompetition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int minParticipants = Integer.parseInt(request.getParameter("min_participants"));
        int maxParticipants = Integer.parseInt(request.getParameter("max_participants"));

        competitionsBean.createCompetition(name, description, minParticipants, maxParticipants);

        response.sendRedirect(request.getContextPath() + "/Competitions");
    }
}