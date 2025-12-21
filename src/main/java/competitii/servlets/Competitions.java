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

/**
 * Servlet responsabil pentru afișarea listei de competiții.
 * Include funcționalități de căutare, paginare și verificarea statusului de înscriere.
 */
@WebServlet(name = "Competitions", value = "/Competitions")
public class Competitions extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Preluăm toate competițiile și aplicăm filtrarea (Search)
        String keyword = request.getParameter("search");
        List<Competition> allCompetitions = competitionsBean.getAllCompetitions();

        if (isKeywordValid(keyword)) {
            allCompetitions = filterCompetitions(allCompetitions, keyword.toLowerCase());
        }

        // 2. Logica de paginare simplificată
        int currentPage = parsePageParam(request.getParameter("page"));
        int totalCompetitions = allCompetitions.size();
        int totalPages = (int) Math.ceil((double) totalCompetitions / PAGE_SIZE);

        List<Competition> paginatedList = getPaginatedSublist(allCompetitions, currentPage, totalCompetitions);

        // 3. Verificăm cine este înscris (pentru a afișa butoane diferite în UI)
        Map<Long, Boolean> appliedStatus = checkApplicationsForUser(request, paginatedList);

        // 4. Setăm atributele pentru JSP
        request.setAttribute("competitions", paginatedList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("searchKeyword", keyword);
        request.setAttribute("appliedStatus", appliedStatus);

        request.getRequestDispatcher("/WEB-INF/pages/competitions.jsp").forward(request, response);
    }

    // --- METODE HELPER PENTRU CLEAN CODE ---

    private boolean isKeywordValid(String keyword) {
        return keyword != null && !keyword.trim().isEmpty();
    }

    private List<Competition> filterCompetitions(List<Competition> list, String lowerKeyword) {
        return list.stream()
                .filter(c -> c.getName().toLowerCase().contains(lowerKeyword) ||
                        c.getDescription().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    private int parsePageParam(String pageParam) {
        try {
            if (pageParam != null) return Math.max(1, Integer.parseInt(pageParam));
        } catch (NumberFormatException e) { /* default to 1 */ }
        return 1;
    }

    private List<Competition> getPaginatedSublist(List<Competition> list, int page, int total) {
        int fromIndex = (page - 1) * PAGE_SIZE;
        if (fromIndex >= total || fromIndex < 0) return List.of();

        int toIndex = Math.min(fromIndex + PAGE_SIZE, total);
        return list.subList(fromIndex, toIndex);
    }

    private Map<Long, Boolean> checkApplicationsForUser(HttpServletRequest request, List<Competition> list) {
        Map<Long, Boolean> statusMap = new HashMap<>();
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Verificăm înscrierile doar dacă utilizatorul este logat și are rol de STUDENT sau ELEV
        if (user != null && ("STUDENT".equals(user.getRole()) || "ELEV".equals(user.getRole()))) {
            for (Competition comp : list) {
                boolean hasApplied = competitionsBean.hasStudentApplied(user.getId(), comp.getId());
                statusMap.put(comp.getId(), hasApplied);
            }
        }
        return statusMap;
    }
}