package competitii.servlets;

import competitii.beans.UsersBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet pentru înregistrarea utilizatorilor noi.
 * Gestionează atribuirea rolului în funcție de tipul de email introdus.
 */
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

        // Preluăm cele 3 tipuri de email posibile
        String emailStudent = request.getParameter("email_student");
        String emailElev = request.getParameter("email_elev");
        String emailStaff = request.getParameter("email_staff");

        String finalEmail = "";
        String finalRole = "";

        // Determinăm rolul și email-ul valid
        if (isValid(emailStudent)) {
            if (!emailStudent.toLowerCase().endsWith("@ulbsibiu.ro")) {
                sendError(request, response, "Email-ul de student trebuie să conțină @ulbsibiu.ro!");
                return;
            }
            finalEmail = emailStudent;
            finalRole = "STUDENT";
        } else if (isValid(emailElev)) {
            if (!emailElev.toLowerCase().endsWith("@elev.com")) {
                sendError(request, response, "Email-ul de elev trebuie să conțină @elev.com!");
                return;
            }
            finalEmail = emailElev;
            finalRole = "ELEV";
        } else if (isValid(emailStaff)) {
            finalEmail = emailStaff;
            finalRole = "REPRESENTATIVE";
        } else {
            sendError(request, response, "Te rugăm să completezi una dintre cele 3 secțiuni de email!");
            return;
        }

        // Încercăm crearea utilizatorului și verificăm dacă succesul a fost confirmat de Bean
        boolean success = usersBean.createUser(username, finalEmail, password, finalRole);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/Login?registered=true");
        } else {
            sendError(request, response, "Numele de utilizator este deja folosit. Alege altul!");
        }
    }

    /**
     * Verifică dacă un string de intrare nu este null sau gol.
     */
    private boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Trimite un mesaj de eroare către pagina de înregistrare.
     */
    private void sendError(HttpServletRequest req, HttpServletResponse resp, String msg) throws ServletException, IOException {
        req.setAttribute("error", msg);
        req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
    }
}