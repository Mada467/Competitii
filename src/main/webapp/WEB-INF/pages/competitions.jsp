<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Lista Competiții">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1>Competiții Disponibile</h1>
    <a href="${pageContext.request.contextPath}/AddCompetition" class="btn btn-primary shadow-sm">
      + Adaugă Competiție Nouă
    </a>
  </div>

  <form method="POST" action="${pageContext.request.contextPath}/DeleteCompetitions">
    <div class="list-group shadow-sm mb-4">
      <c:forEach var="comp" items="${competitions}">
        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center p-3">
          <div class="d-flex align-items-center">
            <input class="form-check-input me-3" type="checkbox" name="competition_ids" value="${comp.id}" style="width: 20px; height: 20px;">

            <div>
              <h5 class="mb-1 text-primary">${comp.name}</h5>
              <p class="mb-1 text-muted">${comp.description}</p>
              <small class="badge bg-light text-dark border">
                Participanți: ${comp.minParticipants} - ${comp.maxParticipants}
              </small>
            </div>
          </div>

          <a href="${pageContext.request.contextPath}/EditCompetition?id=${comp.id}" class="btn btn-sm btn-outline-secondary px-3">
            Editează
          </a>
        </div>
      </c:forEach>

      <c:if test="${empty competitions}">
        <div class="alert alert-info m-3">
          Momentan nu există competiții în baza de date. Folosește butonul de sus pentru a adăuga una.
        </div>
      </c:if>
    </div>

    <c:if test="${not empty competitions}">
      <div class="card p-3 bg-light border-0">
        <p class="text-danger small mb-2">* Selectează competițiile pe care dorești să le elimini:</p>
        <button type="submit" class="btn btn-danger shadow-sm" style="width: fit-content;">
          Șterge Selectate
        </button>
      </div>
    </c:if>
  </form>
</t:pageTemplate>