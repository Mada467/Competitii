<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="jakarta.tags.core" %>
<t:pageTemplate pageTitle="Lista Competitii">
  <div class="container mt-4">
    <h2>Competiții Active</h2>
    <p class="text-muted">Aici poți vedea toate competițiile la care te poți înscrie.</p>

    <ul class="list-group shadow-sm">
      <c:forEach var="comp" items="${competitions}">
        <li class="list-group-item d-flex justify-content-between align-items-center">
            ${comp}
          <button class="btn btn-sm btn-primary">Detalii</button>
        </li>
      </c:forEach>
    </ul>
  </div>
</t:pageTemplate>