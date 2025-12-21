<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Gestionare Participanți">
    <div class="container py-4">

            <%-- Header cu buton de întoarcere --%>
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="fw-bold text-dark">
                <i class="bi bi-people-fill text-primary me-2"></i>Participanți: ${competition.name}
            </h2>
            <a href="${pageContext.request.contextPath}/Competitions" class="btn btn-outline-secondary shadow-sm">
                <i class="bi bi-arrow-left"></i> Înapoi
            </a>
        </div>

            <%-- Dashboard Statistici --%>
        <div class="row g-3 mb-4">
            <div class="col-md-4">
                <div class="card shadow-sm border-0 h-100 bg-white">
                    <div class="card-body text-center">
                        <div class="text-muted small fw-bold text-uppercase mb-1">Total Înscriși</div>
                        <div class="h3 mb-0 fw-bold">${total} <span class="text-muted fs-6">/ ${competition.maxParticipants}</span></div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0 h-100 bg-white">
                    <div class="card-body text-center">
                        <div class="text-success small fw-bold text-uppercase mb-1">Aprobați</div>
                        <div class="h3 mb-0 fw-bold text-success">${aprobati}</div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0 h-100 bg-white border-bottom border-warning border-4">
                    <div class="card-body text-center">
                        <div class="text-warning small fw-bold text-uppercase mb-1">În așteptare</div>
                        <div class="h3 mb-0 fw-bold text-warning">${asteptare}</div>
                    </div>
                </div>
            </div>
        </div>

            <%-- Bara de Progres Capacitate --%>
        <div class="card shadow-sm border-0 mb-4 bg-white p-3">
            <div class="d-flex justify-content-between align-items-center mb-2">
                <span class="fw-bold small text-muted">GRAD DE OCUPARE:</span>
                <span class="badge ${procent >= 90 ? 'bg-danger' : 'bg-primary'} rounded-pill">${procent}%</span>
            </div>
            <div class="progress rounded-pill" style="height: 12px;">
                <div class="progress-bar progress-bar-striped progress-bar-animated
                    ${procent >= 100 ? 'bg-danger' : (procent >= 80 ? 'bg-warning' : 'bg-info')}"
                     role="progressbar" style="width: ${procent}%">
                </div>
            </div>
        </div>

            <%-- Tabel Participanți --%>
        <div class="card shadow-sm border-0 overflow-hidden">
            <div class="table-responsive">
                <table class="table table-hover align-middle mb-0">
                    <thead class="table-light border-bottom">
                    <tr>
                        <th class="ps-4">Utilizator</th>
                        <th>Email Oficial</th>
                        <th>Status Aplicație</th>
                        <th class="text-center pe-4">Decizie</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="app" items="${applications}">
                        <tr>
                            <td class="ps-4">
                                <div class="fw-bold">${app.student.username}</div>
                                <div class="text-muted small">ID: #${app.id}</div>
                            </td>
                            <td>
                                <span class="text-muted"><i class="bi bi-envelope me-1"></i>${app.student.email}</span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${app.status == 'ACCEPTED'}">
                                            <span class="badge bg-success-subtle text-success border border-success-subtle px-3">
                                                <i class="bi bi-check-circle-fill me-1"></i> Aprobat
                                            </span>
                                    </c:when>
                                    <c:when test="${app.status == 'REJECTED'}">
                                            <span class="badge bg-danger-subtle text-danger border border-danger-subtle px-3">
                                                <i class="bi bi-x-circle-fill me-1"></i> Respins
                                            </span>
                                    </c:when>
                                    <c:otherwise>
                                            <span class="badge bg-warning-subtle text-dark border border-warning-subtle px-3">
                                                <i class="bi bi-clock-history me-1"></i> În așteptare
                                            </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="text-center pe-4">
                                <c:if test="${app.status == 'PENDING'}">
                                    <div class="btn-group shadow-sm">
                                        <form action="${pageContext.request.contextPath}/ApproveStudent" method="POST" class="d-inline">
                                            <input type="hidden" name="application_id" value="${app.id}">
                                            <input type="hidden" name="competition_id" value="${competition.id}">
                                            <button type="submit" class="btn btn-sm btn-success px-3 border-end">Aprobă</button>
                                        </form>
                                        <form action="${pageContext.request.contextPath}/RejectStudent" method="POST" class="d-inline">
                                            <input type="hidden" name="application_id" value="${app.id}">
                                            <input type="hidden" name="competition_id" value="${competition.id}">
                                            <button type="submit" class="btn btn-sm btn-danger px-3">Respinge</button>
                                        </form>
                                    </div>
                                </c:if>
                                <c:if test="${app.status != 'PENDING'}">
                                    <span class="text-muted small italic">Decizie procesată</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty applications}">
                        <tr>
                            <td colspan="4" class="text-center py-5 text-muted">
                                <i class="bi bi-inbox fs-2 d-block mb-2"></i>
                                Nu există înscrieri pentru această competiție.
                            </td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</t:pageTemplate>