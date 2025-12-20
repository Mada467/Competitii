<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Autentificare - Competiții</title>
  <link rel="stylesheet" href="CSS/login.css">
</head>
<body>
<div class="login-container">
  <div class="logo-container">
    <div class="logo">🏆</div>
    <h1>Platformă Competiții</h1>
    <div class="subtitle">Autentifică-te pentru a continua</div>
  </div>

  <div class="decorative-line"></div>

  <% if(request.getParameter("error") != null) { %>
  <div class="error">
    ⚠️ Nume de utilizator sau parolă incorectă!
  </div>
  <% } %>

  <!-- Schimbat action pentru a merge direct la index.jsp -->
  <form action="index.jsp" method="GET">
    <div class="form-group">
      <label>Nume Utilizator</label>
      <input type="text" name="username" required placeholder="Introdu numele tău">
    </div>

    <div class="form-group">
      <label>Parolă</label>
      <input type="password" name="password" required placeholder="Introdu parola">
    </div>

    <button type="submit" class="btn">Intră în Sistem</button>
  </form>

  <div class="register-link">
    Nu ai cont? <a href="register.jsp">Înregistrează-te aici</a>
  </div>
</div>
</body>
</html>