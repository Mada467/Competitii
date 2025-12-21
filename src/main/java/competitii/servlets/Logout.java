package competitii.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

/**
 * Servlet responsabil pentru terminarea sesiunii utilizatorului.
 * Curăță toate datele stocate în sesiune și redirecționează către index.
 */
@WebServlet(name = "Logout", value = "/Logout")
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Preluăm sesiunea existentă fără a crea una nouă (false)
        HttpSession session = request.getSession(false);

        if (session != null) {
            // 2. Încheiem sesiunea și ștergem toate atributele (cum ar fi obiectul "user")
            session.invalidate();
        }

        // 3. Setăm headere pentru a preveni browser-ul să păstreze pagini protejate în Cache
        // Astfel, dacă utilizatorul apasă butonul "Back", nu va vedea datele sensibile
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies

        // 4. Redirecționăm către rădăcina aplicației (pagina de Login sau Index)
        response.sendRedirect(request.getContextPath() + "/");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // De cele mai multe ori, logout-ul se face prin link (GET),
        // dar delegăm și cererile POST către doGet pentru siguranță.
        doGet(request, response);
    }
}