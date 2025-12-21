package competitii.servlets;

import competitii.beans.UsersBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Register", value = "/Register")
public class Register extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String emailStudent = request.getParameter("email_student");
        String emailElev = request.getParameter("email_elev");
        String emailStaff = request.getParameter("email_staff");

        String finalEmail = "";
        String finalRole = "";

        if (emailStudent != null && !emailStudent.trim().isEmpty()) {
            if (!emailStudent.toLowerCase().endsWith("@ulbsibiu.ro")) {
                sendError(request, response, "Email-ul de student trebuie să fie @ulbsibiu.ro!");
                return;
            }
            finalEmail = emailStudent;
            finalRole = "STUDENT";
        }
        else if (emailElev != null && !emailElev.trim().isEmpty()) {
            if (!emailElev.toLowerCase().endsWith("@elev.com")) {
                sendError(request, response, "Email-ul de elev trebuie să fie @elev.com!");
                return;
            }
            finalEmail = emailElev;
            finalRole = "ELEV"; // Salvăm ca ELEV
        }
        else if (emailStaff != null && !emailStaff.trim().isEmpty()) {
            finalEmail = emailStaff;
            finalRole = "REPRESENTATIVE";
        }
        else {
            sendError(request, response, "Te rugăm să completezi una dintre cele 3 casete!");
            return;
        }

        usersBean.createUser(username, finalEmail, password, finalRole);
        response.sendRedirect(request.getContextPath() + "/Login?registered=true");
    }

    private void sendError(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        req.setAttribute("error", msg);
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}