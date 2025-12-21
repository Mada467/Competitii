<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Editează Competiția">
    <div class="row justify-content-center">
        <div class="col-md-10">
                <%-- Breadcrumb/Navigare rapidă --%>
            <nav aria-label="breadcrumb" class="mb-3">
                <ol class="breadcrumb">
                    <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/Competitions">Competiții</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Editare: ${competition.name}</li>
                </ol>
            </nav>

            <div class="card shadow-sm border-0 overflow-hidden">
                    <%-- Header cu gradient discret pentru un look profesional --%>
                <div class="card-header bg-warning py-3">
                    <h2 class="h4 mb-0 fw-bold text-dark">
                        <i class="bi bi-pencil-square me-2"></i>Modificare Competiție
                    </h2>
                </div>

                <div class="card-body p-4">
                    <form method="POST" action="${pageContext.request.contextPath}/EditCompetition">
                            <%-- Câmp ascuns obligatoriu pentru identificarea entității în baza de date --%>
                        <input type="hidden" name="id" value="${competition.id}">

                        <div class="row mb-4">
                            <div class="col-12">
                                <label class="form-label fw-bold text-secondary small text-uppercase">Nume Competiție</label>
                                <input type="text" name="name" class="form-control form-control-lg shadow-sm"
                                       value="${competition.name}" required>
                            </div>
                        </div>

                        <div class="mb-4">
                            <label class="form-label fw-bold text-secondary small text-uppercase">Descriere</label>
                            <textarea name="description" class="form-control shadow-sm" rows="4"
                                      placeholder="Detalii despre tematică, premii sau regulament..." required>${competition.description}</textarea>
                        </div>

                        <div class="p-3 bg-light rounded-3 mb-4 border">
                            <h5 class="fw-bold mb-3 text-dark border-bottom pb-2">
                                <i class="bi bi-gear-fill me-2"></i>Configurare Înscrieri & Status
                            </h5>

                            <div class="row g-3">
                                    <%-- Folosim variabilele formattedStart și formattedDeadline calculate în Servlet --%>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Data Deschidere</label>
                                    <input type="datetime-local" name="start_date" class="form-control shadow-sm"
                                           value="${formattedStart}" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold">Deadline Final</label>
                                    <input type="datetime-local" name="deadline" class="form-control shadow-sm"
                                           value="${formattedDeadline}" required>
                                </div>

                                <div class="col-md-3">
                                    <label class="form-label fw-bold">Min. Participanți</label>
                                    <input type="number" name="min_participants" class="form-control shadow-sm"
                                           value="${competition.minParticipants}" min="1" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label fw-bold">Max. Participanți</label>
                                    <input type="number" name="max_participants" class="form-control shadow-sm"
                                           value="${competition.maxParticipants}" min="1" required>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label fw-bold text-primary">Status Curent</label>
                                    <select name="status" class="form-select shadow-sm border-primary-subtle">
                                        <option value="OPEN" ${competition.status == 'OPEN' ? 'selected' : ''}>
                                            ✅ Deschisă (Înscrieri active)
                                        </option>
                                        <option value="CLOSED" ${competition.status == 'CLOSED' ? 'selected' : ''}>
                                            🔒 Închisă temporar
                                        </option>
                                        <option value="COMPLETED" ${competition.status == 'COMPLETED' ? 'selected' : ''}>
                                            🏁 Finalizată
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="mb-4">
                            <div class="form-check form-switch p-3 border rounded shadow-sm bg-white">
                                <input type="checkbox" name="is_internal" class="form-check-input ms-0 me-2"
                                       id="checkInternal" ${competition.internal ? 'checked' : ''}>
                                <label class="form-check-label fw-bold text-dark" for="checkInternal">
                                    Restricție Instituțională (@student.upt.ro)
                                </label>
                                <div class="form-text ms-4">Dacă este activată, doar utilizatorii cu email oficial se pot înscrie.</div>
                            </div>
                        </div>

                            <%-- Zona de butoane --%>
                        <div class="mt-4 border-top pt-3 d-flex justify-content-end gap-2">
                            <a href="${pageContext.request.contextPath}/Competitions"
                               class="btn btn-outline-secondary px-4">
                                <i class="bi bi-x-lg"></i> Anulează
                            </a>
                            <button type="submit" class="btn btn-warning px-5 fw-bold text-dark shadow-sm">
                                <i class="bi bi-check-lg"></i> Salvează Modificările
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</t:pageTemplate>