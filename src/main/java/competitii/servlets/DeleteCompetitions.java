package competitii.servlets;

import competitii.beans.CompetitionsBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteCompetitions", value = "/DeleteCompetitions")
public class DeleteCompetitions extends HttpServlet {

    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Luam toate ID-urile bifate din checkbox-uri
        String[] competitionIdsAsString = request.getParameterValues("competition_ids");

        if (competitionIdsAsString != null) {
            List<Long> competitionIds = new ArrayList<>();
            for (String idAsString : competitionIdsAsString) {
                competitionIds.add(Long.parseLong(idAsString));
            }
            // Trimitem lista de ID-uri catre Bean pentru stergere
            competitionsBean.deleteCompetitions(competitionIds);
        }

        // Dupa stergere, ne intoarcem la lista actualizata
        response.sendRedirect(request.getContextPath() + "/Competitions");
    }
}