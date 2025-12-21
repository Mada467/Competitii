<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Editează Competiția">
    <div class="card shadow-sm border-0">
        <div class="card-header bg-warning text-dark py-3">
            <h2 class="h4 mb-0">Modificare Competiție: ${competition.name}</h2>
        </div>
        <div class="card-body p-4">

            <form method="POST" action="${pageContext.request.contextPath}/EditCompetition">
                    <%-- Câmp ascuns pentru ID --%>
                <input type="hidden" name="id" value="${competition.id}">

                <div class="row mb-3">
                    <div class="col-md-12">
                        <label class="form-label fw-bold">Nume Competiție</label>
                        <input type="text" name="name" class="form-control"
                               value="${competition.name}" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label fw-bold">Descriere</label>
                    <textarea name="description" class="form-control" rows="4" required>${competition.description}</textarea>
                </div>

                <hr class="my-4">
                <h5 class="text-muted mb-3">Configurare Înscrieri & Status</h5>

                <div class="row">
                        <%-- FIX: Folosim datele formatate din servlet --%>
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Start Înscrieri</label>
                        <input type="datetime-local" name="start_date" class="form-control"
                               value="${formattedStart}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Deadline Înscrieri</label>
                        <input type="datetime-local" name="deadline" class="form-control"
                               value="${formattedDeadline}" required>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-3 mb-3">
                        <label class="form-label fw-bold">Min Participanți</label>
                        <input type="number" name="min_participants" class="form-control"
                               value="${competition.minParticipants}" required>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="form-label fw-bold">Max Participanți</label>
                        <input type="number" name="max_participants" class="form-control"
                               value="${competition.maxParticipants}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label fw-bold">Status Competiție</label>
                        <select name="status" class="form-select">
                            <option value="OPEN" ${competition.status == 'OPEN' ? 'selected' : ''}>
                                Deschisă (Înscrieri active)
                            </option>
                            <option value="CLOSED" ${competition.status == 'CLOSED' ? 'selected' : ''}>
                                Închisă temporar
                            </option>
                            <option value="COMPLETED" ${competition.status == 'COMPLETED' ? 'selected' : ''}>
                                Finalizată (Afișează rezultate)
                            </option>
                        </select>
                    </div>
                </div>

                <div class="mb-3 form-check">
                    <input type="checkbox" name="is_internal" class="form-check-input"
                           id="checkInternal" ${competition.internal ? 'checked' : ''}>
                    <label class="form-check-label fw-bold" for="checkInternal">
                        Competiție Internă (Verifică email @student.upt.ro)
                    </label>
                </div>

                <div class="mt-4 border-top pt-3 text-end">
                    <a href="${pageContext.request.contextPath}/Competitions"
                       class="btn btn-outline-secondary me-2">Anulează</a>
                    <button type="submit" class="btn btn-warning px-4">Salvează Modificările</button>
                </div>
            </form>
        </div>
    </div>
</t:pageTemplate>
