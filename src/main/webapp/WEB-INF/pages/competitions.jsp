<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>

<t:pageTemplate pageTitle="Lista Competitii">
  <h1 class="mb-4">Competitii Disponibile</h1>

  <div class="list-group shadow-sm">
    <c:forEach var="comp" items="${competitions}">
      <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
        <div>
          <h5 class="mb-1">${comp.name}</h5>
          <p class="mb-1">${comp.description}</p>
          <small>Participanti: ${comp.minParticipants} - ${comp.maxParticipants}</small>
        </div>
        <a href="#" class="btn btn-sm btn-outline-primary">Vezi detalii</a>
      </div>
    </c:forEach>

    <c:if test="${empty competitions}">
      <div class="alert alert-info">Momentan nu exista competitii in baza de date.</div>
    </c:if>
  </div>
</t:pageTemplate>