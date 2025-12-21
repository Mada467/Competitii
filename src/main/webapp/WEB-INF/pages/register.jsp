<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Înregistrare Cont Nou">
    <div class="row justify-content-center align-items-center py-5">
        <div class="col-md-7 col-lg-6">

            <div class="text-center mb-4">
                <h2 class="fw-bold text-dark">Alătură-te comunității CSEE</h2>
                <p class="text-muted">Alege profilul potrivit și începe înscrierea la competiții</p>
            </div>

            <div class="card shadow-lg border-0 rounded-4">
                <div class="card-header bg-dark text-white text-center py-3 border-0">
                    <h4 class="mb-0 fw-bold"><i class="bi bi-person-plus-fill me-2"></i>Creează Cont</h4>
                </div>
                <div class="card-body p-4 p-md-5">

                        <%-- Afișare erori de validare (ex: email invalid sau utilizator existent) --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center border-0 shadow-sm mb-4" role="alert">
                            <i class="bi bi-exclamation-octagon-fill me-2"></i>
                            <div>${error}</div>
                        </div>
                    </c:if>

                    <form method="POST" action="${pageContext.request.contextPath}/Register">

                        <div class="row mb-4">
                            <div class="col-md-6">
                                <label class="form-label fw-bold small text-uppercase text-muted">Nume utilizator</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white"><i class="bi bi-person"></i></span>
                                    <input type="text" name="username" class="form-control" placeholder="matei20" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold small text-uppercase text-muted">Parolă</label>
                                <div class="input-group">
                                    <span class="input-group-text bg-white"><i class="bi bi-key"></i></span>
                                    <input type="password" name="password" class="form-control" placeholder="Min. 6 caractere" required>
                                </div>
                            </div>
                        </div>

                        <div class="separator d-flex align-items-center text-center my-4">
                            <span class="px-3 fw-bold small text-primary text-uppercase" style="background: white; z-index: 1;">Identificare Rol</span>
                            <hr class="w-100" style="margin-left: -100px;">
                        </div>

                        <p class="text-center text-muted small mb-4">
                            <i class="bi bi-info-circle"></i> Vă rugăm să completați <strong>doar una</strong> dintre cele trei opțiuni de mai jos:
                        </p>

                        <div class="mb-3 p-3 border rounded-3 bg-light hover-shadow transition-all">
                            <label class="form-label fw-bold text-primary mb-1">
                                <i class="bi bi-mortarboard-fill"></i> SUNT STUDENT (ULBS)
                            </label>
                            <input type="email" name="email_student" class="form-control" placeholder="prenume.nume@ulbsibiu.ro">
                            <div class="form-text mt-2 small">Necesită adresă oficială <strong>@ulbsibiu.ro</strong></div>
                        </div>

                        <div class="mb-3 p-3 border rounded-3 bg-light hover-shadow transition-all">
                            <label class="form-label fw-bold text-warning mb-1">
                                <i class="bi bi-pencil-square"></i> SUNT ELEV (LICEU)
                            </label>
                            <input type="email" name="email_elev" class="form-control" placeholder="nume@elev.com">
                            <div class="form-text mt-2 small">Necesită adresă de tip <strong>@elev.com</strong></div>
                        </div>

                        <div class="mb-4 p-3 border rounded-3 bg-light hover-shadow transition-all">
                            <label class="form-label fw-bold text-secondary mb-1">
                                <i class="bi bi-briefcase-fill"></i> REPREZENTANT / STAFF
                            </label>
                            <input type="email" name="email_staff" class="form-control" placeholder="nume@staff.ro">
                            <div class="form-text mt-2 small">Pentru organizatori și administratori de competiții.</div>
                        </div>

                        <button type="submit" class="btn btn-success w-100 py-3 fw-bold shadow-sm rounded-3 mt-2">
                            FINALIZEAZĂ ÎNREGISTRAREA <i class="bi bi-arrow-right-short ms-1"></i>
                        </button>
                    </form>

                    <div class="mt-4 text-center">
                        <span class="text-muted small">Ai deja un cont?</span>
                        <a href="${pageContext.request.contextPath}/Login" class="text-decoration-none fw-bold ms-1">Autentifică-te</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>