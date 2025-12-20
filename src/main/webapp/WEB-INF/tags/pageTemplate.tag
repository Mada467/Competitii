<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" required="true"%>

<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Competiții CSEE</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <div class="navbar-nav">
                <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Home</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/Competitions">Competiții</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/AddCompetition">Adaugă Competiție</a>
                <a class="nav-link" href="${pageContext.request.contextPath}/about.jsp">About</a>
            </div>
        </div>
    </div>
</nav>

<main class="container shadow-sm p-4 bg-white rounded" style="min-height: 400px;">
    <jsp:doBody/>
</main>

<footer class="container mt-5 py-3 border-top text-center text-muted">
    <p>&copy; 2024 Alexandru Dorobanțiu - CSEE</p>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>