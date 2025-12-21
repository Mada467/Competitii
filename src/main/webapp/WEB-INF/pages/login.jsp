<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Autentificare">
    <div class="row justify-content-center">
        <div class="col-md-4 mt-5">
            <div class="card shadow-lg border-0">
                <div class="card-header bg-dark text-white text-center py-3">
                    <h4 class="mb-0">Autentificare CSEE</h4>
                </div>
                <div class="card-body p-4">

                        <%-- Mesaj de eroare la autentificare --%>
                    <c:if test="${not empty login_error}">
                        <div class="alert alert-danger py-2 small">${login_error}</div>
                    </c:if>

                        <%-- Mesaj de succes după înregistrare (provenit din Register servlet) --%>
                    <c:if test="${param.registered == 'true'}">
                        <div class="alert alert-success py-2 small">Cont creat cu succes! Te poți autentifica.</div>
                    </c:if>

                    <form method="POST" action="${pageContext.request.contextPath}/Login">
                        <div class="mb-3">
                            <label class="form-label fw-bold">Utilizator</label>
                            <input type="text" name="username" class="form-control" placeholder="Introdu username-ul" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label fw-bold">Parolă</label>
                            <input type="password" name="password" class="form-control" placeholder="Introdu parola" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100 py-2">Intră în cont</button>
                    </form>

                    <div class="mt-4 text-center">
                        <p class="small text-muted mb-0">Nu ai un cont?</p>
                        <a href="${pageContext.request.contextPath}/Register" class="text-decoration-none">Înregistrează-te ca Student</a>
                    </div>
                </div>
            </div>

                <%-- Informație utilă conform CRS pentru studenți --%>
            <div class="mt-3 text-center text-muted small">
                <i class="bi bi-info-circle"></i> Pentru competițiile interne, folosiți adresa de email instituțională.
            </div>
        </div>
    </div>
</t:pageTemplate>