<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="pageTitle" required="true"%>

<!DOCTYPE html>
<html lang="ro">
<head>
    <title>${pageTitle} | CSEE Competitions</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

    <style>
        :root {
            --csee-primary: #0d6efd;
            --csee-warning: #ffc107;
        }
        body {
            background-color: #f0f2f5;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .navbar-brand { font-weight: 800; letter-spacing: 0.5px; }

        /* Main content styling */
        main {
            border-top: 5px solid var(--csee-primary);
            flex: 1 0 auto; /* Împinge footer-ul jos dacă pagina e goală */
        }

        /* Hover effects pentru navigare */
        .nav-link { transition: color 0.2s ease; }
        .nav-link:hover { color: var(--csee-warning) !important; }

        .footer {
            flex-shrink: 0;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top shadow-sm">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
            <i class="bi bi-trophy-fill me-2 text-warning"></i>
            <span>CSEE <span class="text-warning">COMP</span></span>
        </a>

        <button class="navbar-toggler border-0" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">
                        <i class="bi bi-house-door-fill me-1"></i> Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/Competitions">
                        <i class="bi bi-grid-3x3-gap-fill me-1"></i> Listă Competiții
                    </a>
                </li>

                <c:if test="${sessionScope.user.role == 'REPRESENTATIVE'}">
                    <li class="nav-item">
                        <a class="nav-link text-warning fw-bold" href="${pageContext.request.contextPath}/AddCompetition">
                            <i class="bi bi-plus-circle-fill me-1"></i> Competiție Nouă
                        </a>
                    </li>
                </c:if>
            </ul>

            <ul class="navbar-nav ms-auto align-items-center">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle d-flex align-items-center" href="#" id="userDropdown" role="button" data-bs-toggle="dropdown">
                                <span class="badge bg-primary-subtle text-primary border border-primary-subtle me-2 px-3 py-2">
                                    <i class="bi bi-person-fill"></i> ${sessionScope.user.username}
                                </span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow border-0">
                                <li class="dropdown-header text-uppercase small fw-bold">Rol: ${sessionScope.user.role}</li>
                                <li><hr class="dropdown-divider"></li>
                                <li>
                                    <a class="dropdown-item text-danger" href="${pageContext.request.contextPath}/Logout">
                                        <i class="bi bi-box-arrow-right me-2"></i> Logout
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="btn btn-outline-light btn-sm me-2 px-3" href="${pageContext.request.contextPath}/Login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="btn btn-warning btn-sm px-3 fw-bold text-dark" href="${pageContext.request.contextPath}/Register">Cont Nou</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<main class="container my-5 shadow-sm p-4 p-md-5 bg-white rounded-4">
    <jsp:doBody/>
</main>

<footer class="footer mt-auto py-4 bg-white border-top">
    <div class="container text-center">
        <div class="row align-items-center">
            <div class="col-md-6 text-md-start">
                <span class="text-muted fw-bold">Departamentul CSEE</span>
                <span class="text-muted ms-2 small border-start ps-2">Software Management Competiții</span>
            </div>
            <div class="col-md-6 text-md-end">
                <span class="text-muted small">&copy; 2025 | Creat pentru succesul studenților.</span>
            </div>
        </div>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>