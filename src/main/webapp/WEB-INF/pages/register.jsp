<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Înregistrare">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-lg border-0">
                <div class="card-header bg-dark text-white text-center py-3">
                    <h4 class="mb-0 fw-bold">Creează Cont Nou</h4>
                </div>
                <div class="card-body p-4">

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger shadow-sm fw-bold mb-4">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
                        </div>
                    </c:if>

                    <form method="POST" action="${pageContext.request.contextPath}/Register">
                        <div class="mb-4">
                            <label class="form-label fw-bold">Nume utilizator</label>
                            <input type="text" name="username" class="form-control shadow-sm" placeholder="Ex: matei20" required>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold">Parolă</label>
                            <input type="password" name="password" class="form-control shadow-sm" placeholder="Minim 6 caractere" required>
                        </div>

                        <hr>
                        <p class="text-muted small fw-bold">COMPLETEAZĂ DOAR CASETA CARE ȚI SE POTRIVEȘTE:</p>

                        <div class="mb-3 p-3 border-start border-primary border-4 bg-light shadow-sm">
                            <label class="form-label fw-bold text-primary"><i class="bi bi-book"></i> EMAIL STUDENT (ULBS)</label>
                            <input type="email" name="email_student" class="form-control" placeholder="nume@ulbsibiu.ro">
                            <div class="form-text">Trebuie să se termine în @ulbsibiu.ro</div>
                        </div>

                        <div class="mb-3 p-3 border-start border-warning border-4 bg-light shadow-sm">
                            <label class="form-label fw-bold text-warning"><i class="bi bi-pencil"></i> EMAIL ELEV (LICEU)</label>
                            <input type="email" name="email_elev" class="form-control" placeholder="nume@elev.com">
                            <div class="form-text">Trebuie să se termine în @elev.com</div>
                        </div>

                        <div class="mb-4 p-3 border-start border-secondary border-4 bg-light shadow-sm">
                            <label class="form-label fw-bold text-secondary"><i class="bi bi-person-badge"></i> EMAIL REPREZENTANT</label>
                            <input type="email" name="email_staff" class="form-control" placeholder="nume@staff.ro">
                        </div>

                        <button type="submit" class="btn btn-success w-100 py-2 fw-bold shadow">
                            <i class="bi bi-check-circle-fill me-2"></i> ÎNREGISTREAZĂ-TE
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>