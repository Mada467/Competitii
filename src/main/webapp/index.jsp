<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Platformă Competiții</title>
    <link rel="stylesheet" href="CSS/index.css">
</head>
<body>
<div class="header">
    <div class="header-content">
        <div class="logo"> Platformă Competiții</div>
        <nav class="nav">
            <a href="index.jsp">Acasă</a>
            <% if(session.getAttribute("user") != null) { %>
            <a href="my-competitions.jsp">Competițiile Mele</a>
            <a href="logout.jsp">Ieșire</a>
            <% } else { %>
            <a href="login.jsp">Autentificare</a>
            <a href="register.jsp">Înregistrare</a>  <% } %>
        </nav>
    </div>
</div>

<div class="container">
    <div class="welcome-banner">
        <h1>Bine ai venit!</h1>
        <p>Descoperă competițiile disponibile și participă</p>
    </div>

    <div class="tabs">
        <button class="tab active" onclick="showUpcoming()">Competiții Viitoare</button>
        <button class="tab" onclick="showPast()">Competiții Trecute</button>
    </div>

    <div class="search-bar">
        <input type="text" placeholder=" Caută competiții după cuvinte cheie...">
    </div>

    <div class="empty-state">
        <div class="empty-state-icon">📭</div>
        <h2>Momentan nu există competiții</h2>
        <p>Revino mai târziu pentru a vedea competițiile disponibile</p>
    </div>

    <div class="competition-grid" id="competitionGrid" style="display: none;">
    </div>
</div>

<script src="JS/index.js"></script>
</body>
</html>