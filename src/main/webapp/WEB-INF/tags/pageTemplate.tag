<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle" required="true"%>

<!DOCTYPE html>
<html lang="ro">
<head>
    <title>${pageTitle} - CSEE Competitions</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .navbar-brand { font-weight: bold; letter-spacing: 1px; }
        /* Am schimbat bordura de sus să fie albastră închis pentru contrast cu main-ul alb */
        main { border-top: 4px solid #0d6efd; }
        /* Stil pentru link-ul de Competiție Nouă */
        .nav-link.text-warning { font-weight: 700 !important; }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <i class="bi bi-trophy-fill me-2"></i>Competiții CSEE
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/"><i class="bi bi-house-door"></i> Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Competitions"><i class="bi bi-list-ul"></i> Listă Competiții</a>
                </li>

                <%-- ACTUALIZAT: Textul "Competiție Nouă" stilizat cu galben (text-warning) --%>
                <c:if test="${sessionScope.user.role == 'REPRESENTATIVE'}">
                    <li class="nav-item">
                        <a class="nav-link text-warning fw-bold" href="${pageContext.request.contextPath}/AddCompetition">
                            <i class="bi bi-plus-circle-fill"></i> Competiție Nouă
                        </a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav ms-auto align-items-center">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item me-3">
                            <span class="badge bg-secondary p-2">
                                <i class="bi bi-person-circle"></i> ${sessionScope.user.username}
                                <span class="ms-1 text-uppercase" style="font-size: 0.7rem;">(${sessionScope.user.role})</span>
                            </span>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-danger btn-sm px-3" href="${pageContext.request.contextPath}/Logout">
                                <i class="bi bi-box-arrow-right"></i> Logout
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm me-2" href="${pageContext.request.contextPath}/Login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-success btn-sm px-3" href="${pageContext.request.contextPath}/Register">Cont Nou</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<main class="container shadow p-5 bg-white rounded mb-5">
    <jsp:doBody/>
</main>

<footer class="container-fluid bg-white py-4 border-top text-center text-muted">
    <div class="container">
        <p class="mb-1"><strong>Departamentul CSEE</strong> - Software Management Competiții</p>
        <p class="small">&copy; 2025 - Sistem de Management Studențesc</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>