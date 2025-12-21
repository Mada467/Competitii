package competitii.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/Logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Distrugem sesiunea curentă
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Trimitem utilizatorul la pagina de pornire
        response.sendRedirect(request.getContextPath() + "/");
    }
}