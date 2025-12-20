package competitii.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Competitions", value = "/Competitions")
public class Competitions extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simulăm datele pentru "User Story: Listare Competiții"
        List<String> list = new ArrayList<>();
        list.add("Web Development Contest");
        list.add("CyberSecurity Challenge");
        list.add("AI Hackathon 2024");

        // Punem lista în request pentru a fi citită de JSP
        request.setAttribute("competitions", list);

        // Redirecționăm către pagina vizuală (View)
        request.getRequestDispatcher("/WEB-INF/pages/competitions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Va fi folosit la înscrieri (PDF 5)
    }
}