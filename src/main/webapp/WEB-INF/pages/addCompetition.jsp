<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Adaugă Competiție">
    <h2 class="mb-4">Creare Competiție Nouă</h2>

    <form method="POST" action="${pageContext.request.contextPath}/AddCompetition">
        <div class="row">
            <div class="col-md-6 mb-3">
                <label for="name" class="form-label">Nume Competiție</label>
                <input type="text" class="form-control" id="name" name="name" required>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12 mb-3">
                <label for="description" class="form-label">Descriere</label>
                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
            </div>
        </div>

        <div class="row">
            <div class="col-md-3 mb-3">
                <label for="min_participants" class="form-label">Min. Participanți</label>
                <input type="number" class="form-control" id="min_participants" name="min_participants" required>
            </div>
            <div class="col-md-3 mb-3">
                <label for="max_participants" class="form-label">Max. Participanți</label>
                <input type="number" class="form-control" id="max_participants" name="max_participants" required>
            </div>
        </div>

        <div class="mt-4">
            <button type="submit" class="btn btn-primary">Salvează Competiția</button>
            <a href="${pageContext.request.contextPath}/Competitions" class="btn btn-secondary">Înapoi la Listă</a>
        </div>
    </form>
</t:pageTemplate>