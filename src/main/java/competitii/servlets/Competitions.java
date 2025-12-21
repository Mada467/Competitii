package competitii.servlets;

import competitii.beans.CompetitionsBean;
import competitii.entities.Competition;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "Competitions", value = "/Competitions")
public class Competitions extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Competition> competitions = competitionsBean.getAllCompetitions();

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            competitions = competitions.stream()
                    .filter(c -> c.getName().toLowerCase().contains(lowerKeyword) ||
                            c.getDescription().toLowerCase().contains(lowerKeyword))
                    .collect(Collectors.toList());
        }

        int page = 1;
        int pageSize = 5;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null) page = Integer.parseInt(pageParam);
        } catch (NumberFormatException e) { page = 1; }

        int totalCompetitions = competitions.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalCompetitions);
        List<Competition> paginatedList = (fromIndex < totalCompetitions && fromIndex >= 0) ? competitions.subList(fromIndex, toIndex) : List.of();

        Map<Long, Boolean> appliedStatus = new HashMap<>();
        HttpSession session = request.getSession(false);
        User currentUser = (session != null) ? (User) session.getAttribute("user") : null;

        // AICI E CHEIA: Verificăm înscrierile și pentru ELEV, nu doar pentru STUDENT
        if (currentUser != null && ("STUDENT".equals(currentUser.getRole()) || "ELEV".equals(currentUser.getRole()))) {
            for (Competition comp : paginatedList) {
                boolean hasApplied = competitionsBean.hasStudentApplied(currentUser.getId(), comp.getId());
                appliedStatus.put(comp.getId(), hasApplied);
            }
        }

        request.setAttribute("competitions", paginatedList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalCompetitions / pageSize));
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("appliedStatus", appliedStatus);

        request.getRequestDispatcher("/WEB-INF/pages/competitions.jsp").forward(request, response);
    }
}