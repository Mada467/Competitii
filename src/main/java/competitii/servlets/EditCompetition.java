package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.Competition;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EditCompetition", value = "/EditCompetition")
public class EditCompetition extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Competition competition = competitionsBean.findById(id);
        request.setAttribute("competition", competition);
        request.getRequestDispatcher("/WEB-INF/pages/editCompetition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int minP = Integer.parseInt(request.getParameter("min_participants"));
        int maxP = Integer.parseInt(request.getParameter("max_participants"));

        competitionsBean.updateCompetition(id, name, description, minP, maxP);
        response.sendRedirect(request.getContextPath() + "/Competitions");
    }
}