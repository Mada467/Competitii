package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.Competition;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Competitions", value = "/Competitions")
public class Competitions extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Competition> competitions = competitionsBean.getAllCompetitions();
        request.setAttribute("competitions", competitions);
        request.getRequestDispatcher("/WEB-INF/pages/competitions.jsp").forward(request, response);
    }
}