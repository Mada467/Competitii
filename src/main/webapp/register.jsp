<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Înregistrare - Competiții</title>
    <link rel="stylesheet" href="CSS/register.css">
</head>
<body>
<div class="register-container">
    <h1>🏆 Înregistrare</h1>
    <div class="subtitle">Alătură-te platformei</div>

    <div class="decorative-line"></div>

    <form action="login.jsp" method="GET">
        <div class="form-group">
            <label>Nume Utilizator *</label>
            <input type="text" name="username" required placeholder="Alege un nume unic">
        </div>

        <div class="form-group">
            <label>Email *</label>
            <div class="info-text">
                ℹ️ Studenții se vor înregistra cu adresa de email de la facultate (@ulbsibiu.ro)
            </div>
            <input type="email" name="email" required placeholder="adresa@email.com">
        </div>

        <div class="form-group">
            <label>Parolă *</label>
            <input type="password" name="password" required placeholder="Minim 6 caractere">
        </div>

        <button type="submit" class="btn">Înregistrează-te</button>
    </form>

    <div class="login-link">
        Ai deja cont? <a href="login.jsp">Autentifică-te aici</a>
    </div>
</div>
</body>
</html>