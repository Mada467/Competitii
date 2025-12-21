<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Gestionare Participanți">
    <div class="container mt-4">
        <h2><i class="bi bi-people-fill"></i> Participanți: ${competition.name}</h2>

        <div class="row mt-4 mb-3">
            <div class="col-md-4">
                <div class="card p-3 shadow-sm border-0 bg-light text-center">
                    <small class="text-muted fw-bold">TOTAL ÎNSCRIȘI</small>
                    <h3>${total} / ${competition.maxParticipants}</h3>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card p-3 shadow-sm border-0 bg-light text-center">
                    <small class="text-success fw-bold">APROBAȚI</small>
                    <h3 class="text-success">${aprobati}</h3>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card p-3 shadow-sm border-0 bg-light text-center">
                    <small class="text-warning fw-bold">ÎN AȘTEPTARE</small>
                    <h3 class="text-warning">${asteptare}</h3>
                </div>
            </div>
        </div>

        <div class="card p-3 shadow-sm border-0 mb-4">
            <div class="d-flex justify-content-between mb-2">
                <span class="fw-bold">Grad Ocupare:</span>
                <span class="badge bg-primary">${procent}%</span>
            </div>
            <div class="progress" style="height: 15px;">
                <div class="progress-bar ${procent >= 100 ? 'bg-danger' : 'bg-info'}" style="width: ${procent}%"></div>
            </div>
        </div>

        <div class="card shadow-sm border-0">
            <table class="table table-hover mb-0">
                <thead class="table-dark">
                <tr>
                    <th>Nume</th>
                    <th>Email</th>
                    <th>Status</th>
                    <th class="text-center">Acțiuni</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="app" items="${applications}">
                    <tr>
                        <td>${app.student.username}</td>
                        <td>${app.student.email}</td>
                        <td>
                                <span class="badge ${app.status == 'ACCEPTED' ? 'bg-success' : (app.status == 'REJECTED' ? 'bg-danger' : 'bg-warning text-dark')}">
                                    <c:choose>
                                        <c:when test="${app.status == 'ACCEPTED'}">Aprobat</c:when>
                                        <c:when test="${app.status == 'REJECTED'}">Respins</c:when>
                                        <c:otherwise>În așteptare</c:otherwise>
                                    </c:choose>
                                </span>
                        </td>
                        <td class="text-center">
                            <c:if test="${app.status == 'PENDING'}">
                                <div class="d-flex justify-content-center gap-2">
                                    <form action="${pageContext.request.contextPath}/ApproveStudent" method="POST">
                                        <input type="hidden" name="application_id" value="${app.id}">
                                        <input type="hidden" name="competition_id" value="${competition.id}">
                                        <button type="submit" class="btn btn-sm btn-success">Aprobă</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/RejectStudent" method="POST">
                                        <input type="hidden" name="application_id" value="${app.id}">
                                        <input type="hidden" name="competition_id" value="${competition.id}">
                                        <button type="submit" class="btn btn-sm btn-danger">Respinge</button>
                                    </form>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</t:pageTemplate>