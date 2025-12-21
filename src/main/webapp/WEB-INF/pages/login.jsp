<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Autentificare">
    <div class="row justify-content-center align-items-center" style="min-height: 70vh;">
        <div class="col-md-5 col-lg-4">

                <%-- Logo sau Iconiță aplicație --%>
            <div class="text-center mb-4">
                <div class="display-4 text-warning mb-2">
                    <i class="bi bi-person-badge-fill"></i>
                </div>
                <h2 class="fw-bold">Bine ai revenit!</h2>
                <p class="text-muted">Platforma de Competiții CSEE</p>
            </div>

            <div class="card shadow border-0 rounded-4">
                <div class="card-body p-4 p-md-5">

                        <%-- 1. Mesaje de eroare (Sincronizat cu Servlet-ul refactorizat) --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center border-0 shadow-sm mb-4" role="alert">
                            <i class="bi bi-exclamation-circle-fill me-2"></i>
                            <div class="small">${error}</div>
                        </div>
                    </c:if>

                        <%-- 2. Mesaj de succes după înregistrare --%>
                    <c:if test="${param.registered == 'true'}">
                        <div class="alert alert-success d-flex align-items-center border-0 shadow-sm mb-4" role="alert">
                            <i class="bi bi-check-circle-fill me-2"></i>
                            <div class="small">Cont creat cu succes! Te poți autentifica.</div>
                        </div>
                    </c:if>

                    <form method="POST" action="${pageContext.request.contextPath}/Login">
                        <div class="mb-3">
                            <label class="form-label fw-bold small text-uppercase">Utilizator</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white border-end-0"><i class="bi bi-person"></i></span>
                                <input type="text" name="username" class="form-control border-start-0 ps-0"
                                       placeholder="Username" required autofocus>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold small text-uppercase">Parolă</label>
                            <div class="input-group shadow-sm">
                                <span class="input-group-text bg-white border-end-0"><i class="bi bi-lock"></i></span>
                                <input type="password" name="password" class="form-control border-start-0 ps-0"
                                       placeholder="Parola ta" required>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-warning w-100 py-2 fw-bold text-dark shadow-sm">
                            <i class="bi bi-box-arrow-in-right me-1"></i> Intră în cont
                        </button>
                    </form>

                    <div class="mt-4 pt-3 border-top text-center">
                        <p class="small text-muted mb-1">Nu ai un cont pe platformă?</p>
                        <a href="${pageContext.request.contextPath}/Register" class="fw-bold text-primary text-decoration-none">
                            Creează un cont nou <i class="bi bi-arrow-right"></i>
                        </a>
                    </div>
                </div>
            </div>

                <%-- Helper text sub card --%>
            <div class="mt-4 text-center text-muted small px-3">
                <i class="bi bi-shield-check text-success"></i>
                Sistem securizat pentru studenții și reprezentanții <strong>CSEE Sibiu</strong>.
            </div>
        </div>
    </div>
</t:pageTemplate>