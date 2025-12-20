<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Editează Competiția">
    <h2>Editează: ${competition.name}</h2>
    <form method="POST" action="${pageContext.request.contextPath}/EditCompetition">
        <input type="hidden" name="id" value="${competition.id}">

        <div class="mb-3">
            <label class="form-label">Nume</label>
            <input type="text" name="name" class="form-control" value="${competition.name}" required>
        </div>
        <div class="mb-3">
            <label class="form-label">Descriere</label>
            <textarea name="description" class="form-control" rows="3">${competition.description}</textarea>
        </div>
        <div class="row mb-3">
            <div class="col">
                <label class="form-label">Min Participanți</label>
                <input type="number" name="min_participants" class="form-control" value="${competition.minParticipants}" required>
            </div>
            <div class="col">
                <label class="form-label">Max Participanți</label>
                <input type="number" name="max_participants" class="form-control" value="${competition.maxParticipants}" required>
            </div>
        </div>
        <button type="submit" class="btn btn-warning">Actualizează</button>
        <a href="${pageContext.request.contextPath}/Competitions" class="btn btn-secondary">Anulează</a>
    </form>
</t:pageTemplate>
