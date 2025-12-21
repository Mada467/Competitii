package competitii.servlets;

import competitii.beans.CompetitionsBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RejectStudent", value = "/RejectStudent")
public class RejectStudent extends HttpServlet {
    @Inject
    private CompetitionsBean competitionsBean;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long appId = Long.parseLong(request.getParameter("application_id"));
            Long compId = Long.parseLong(request.getParameter("competition_id"));
            competitionsBean.rejectApplication(appId);
            response.sendRedirect(request.getContextPath() + "/ViewApplications?id=" + compId);
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/Competitions?error=true");
        }
    }
}