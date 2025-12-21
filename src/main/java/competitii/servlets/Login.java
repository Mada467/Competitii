package competitii.servlets;

import competitii.beans.UsersBean;
import competitii.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet responsabil pentru autentificarea utilizatorilor.
 * Gestionează sesiunea și redirecționarea către dashboard-ul de competiții.
 */
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {

    @Inject
    private UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Dacă utilizatorul este deja logat, îl trimitem direct la competiții
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/Competitions");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validare de bază pentru a nu interoga baza de date inutil
        if (isInputInvalid(username, password)) {
            sendErrorMessage(request, response, "Te rugăm să completezi toate câmpurile.");
            return;
        }

        User authenticatedUser = usersBean.authenticate(username, password);

        if (authenticatedUser != null) {
            handleSuccessfulLogin(request, response, authenticatedUser);
        } else {
            sendErrorMessage(request, response, "Utilizator sau parolă incorectă!");
        }
    }

    // --- METODE HELPER PENTRU UN COD MAI CURAT ---

    private boolean isInputInvalid(String user, String pass) {
        return user == null || user.isEmpty() || pass == null || pass.isEmpty();
    }

    private void handleSuccessfulLogin(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        // Securitate: Invalidăm orice sesiune veche înainte de a crea una nouă
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        // Creăm sesiunea nouă și stocăm obiectul User
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("user", user);

        response.sendRedirect(request.getContextPath() + "/Competitions");
    }

    private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, String message) throws ServletException, IOException {
        request.setAttribute("error", message);
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }
}