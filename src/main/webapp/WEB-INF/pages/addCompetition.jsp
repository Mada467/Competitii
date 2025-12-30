<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Adăugare Competiție">
    <div class="row justify-content-center">
        <div class="col-md-8">

                <%-- Titlu și buton de întoarcere --%>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="fw-bold">
                    <i class="bi bi-plus-square-fill me-2 text-warning"></i>Competiție Nouă
                </h2>
                <a href="${pageContext.request.contextPath}/Competitions" class="btn btn-outline-secondary btn-sm">
                    <i class="bi bi-arrow-left"></i> Înapoi la listă
                </a>
            </div>

                <%-- Afișare mesaje de eroare trimise de Servlet --%>
            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i> ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="card shadow border-0">
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/AddCompetition" method="POST">

                        <div class="mb-3">
                            <label class="form-label fw-bold">Nume Competiție</label>
                            <input type="text" name="name" class="form-control shadow-sm"
                                   placeholder="Ex: Hackathon CSEE 2025" required>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Descriere Detaliată</label>
                            <textarea name="description" class="form-control shadow-sm" rows="4"
                                      placeholder="Descrie scopul și regulile competiției..." required></textarea>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Min Participanți</label>
                                <input type="number" name="min_participants" class="form-control shadow-sm"
                                       value="1" min="1" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Max Participanți</label>
                                <input type="number" name="max_participants" class="form-control shadow-sm"
                                       value="100" min="1" required>
                            </div>
                        </div>

                        <div class="row mb-4">
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Data Start Înscrieri</label>
                                <input type="datetime-local" name="start_date" class="form-control shadow-sm" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label fw-bold">Termen Limită (Deadline)</label>
                                <input type="datetime-local" name="deadline" class="form-control shadow-sm" required>
                            </div>
                        </div>

                            <%-- Butoane Acțiune --%>
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end border-top pt-3">
                            <button type="reset" class="btn btn-light px-4 border">Resetează</button>
                            <button type="submit" class="btn btn-warning px-5 fw-bold text-dark shadow-sm">
                                <i class="bi bi-cloud-arrow-up-fill me-1"></i> Creează Competiția
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>