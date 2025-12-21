<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:pageTemplate pageTitle="Lista Competiții">
  <div class="mb-4">
    <h1>Competiții CSEE</h1>
  </div>

  <%-- Mesaje de feedback --%>
  <c:if test="${param.withdrawn == 'true'}">
    <div class="alert alert-warning alert-dismissible fade show text-dark fw-bold border-dark shadow-sm" role="alert">
      <i class="bi bi-info-circle-fill me-2"></i> Te-ai retras cu succes din competiție!
      <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <%-- Căutare --%>
  <div class="card mb-4 shadow-sm border-0 bg-light">
    <div class="card-body">
      <form method="GET" action="${pageContext.request.contextPath}/Competitions" class="row g-2">
        <div class="col-md-10">
          <input type="text" name="search" class="form-control"
                 placeholder="Caută după nume sau descriere..." value="${searchKeyword}">
        </div>
        <div class="col-md-2">
          <button type="submit" class="btn btn-dark w-100">Caută</button>
        </div>
      </form>
    </div>
  </div>

  <div class="list-group shadow-sm mb-4">
    <c:forEach var="comp" items="${competitions}">
      <div class="list-group-item list-group-item-action p-4">
        <div class="row align-items-center">

          <c:if test="${sessionScope.user.role == 'REPRESENTATIVE'}">
            <div class="col-auto">
              <input class="form-check-input delete-checkbox" type="checkbox"
                     data-competition-id="${comp.id}" style="width: 22px; height: 22px;">
            </div>
          </c:if>

          <div class="col">
            <div class="d-flex w-100 justify-content-between">
              <h5 class="mb-1 text-primary fw-bold">${comp.name}</h5>
              <small class="text-danger fw-bold">Deadline: ${comp.applicationDeadline}</small>
            </div>
            <p class="mb-2 text-muted">${comp.description}</p>
            <div class="d-flex gap-2 align-items-center">
              <span class="badge bg-info text-dark">Grup: ${comp.minParticipants}-${comp.maxParticipants} pers.</span>
              <span class="badge bg-secondary">Status: ${comp.status}</span>
            </div>
          </div>

          <div class="col-auto text-end">
              <%-- SECTIUNE REPREZENTANT --%>
            <c:if test="${sessionScope.user.role == 'REPRESENTATIVE'}">
              <a href="${pageContext.request.contextPath}/EditCompetition?id=${comp.id}"
                 class="btn btn-sm btn-outline-secondary px-3 mb-2 d-block">Editează</a>
              <a href="${pageContext.request.contextPath}/ViewApplications?id=${comp.id}"
                 class="btn btn-sm btn-info px-3 d-block">Vezi Înscrieri</a>
            </c:if>

              <%-- SECTIUNE STUDENT SAU ELEV --%>
              <%-- MODIFICARE AICI: Am adăugat || role == 'ELEV' --%>
            <c:if test="${sessionScope.user.role == 'STUDENT' || sessionScope.user.role == 'ELEV'}">
              <div class="d-flex flex-column gap-2 align-items-center">
                <c:choose>
                  <c:when test="${appliedStatus[comp.id] == true}">
                    <span class="badge bg-danger px-3 py-2 mb-1 shadow-sm fw-bold">
                        <i class="bi bi-bookmark-check-fill"></i> EȘTI ÎNSCRIS!
                    </span>

                    <form action="${pageContext.request.contextPath}/WithdrawFromCompetition" method="POST" onsubmit="return confirm('Sigur vrei să te retragi?');">
                      <input type="hidden" name="competition_id" value="${comp.id}">
                      <button type="submit" class="btn btn-warning btn-sm px-4 fw-bold text-dark shadow-sm">
                        <i class="bi bi-x-circle"></i> Retrage-te
                      </button>
                    </form>
                  </c:when>

                  <c:when test="${comp.status == 'OPEN'}">
                    <button type="button" class="btn btn-success btn-sm px-4 shadow-sm fw-bold" data-bs-toggle="modal" data-bs-target="#applyModal${comp.id}">
                      Aplică
                    </button>
                  </c:when>

                  <c:otherwise>
                    <span class="badge bg-secondary">Închisă</span>
                  </c:otherwise>
                </c:choose>
              </div>
            </c:if>
          </div>
        </div>
      </div>

      <%-- Modal Înscriere --%>
      <div class="modal fade" id="applyModal${comp.id}" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <form action="${pageContext.request.contextPath}/ApplyToCompetition" method="POST">
              <input type="hidden" name="competition_id" value="${comp.id}">
              <div class="modal-header">
                <h5 class="modal-title">Înscriere: ${comp.name}</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
              </div>
              <div class="modal-body">
                <label class="form-label fw-bold">Detalii aplicație:</label>
                <textarea name="additional_info" class="form-control" rows="3" required placeholder="Ex: Titlu proiect, echipă..."></textarea>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Anulează</button>
                <button type="submit" class="btn btn-success fw-bold">Confirmă Înscrierea</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <c:if test="${not empty competitions && sessionScope.user.role == 'REPRESENTATIVE'}">
    <button type="button" class="btn btn-danger shadow-sm" id="btnDelete">Șterge Selectate</button>
  </c:if>

  <script>
    document.getElementById('btnDelete')?.addEventListener('click', function() {
      const selected = document.querySelectorAll('.delete-checkbox:checked');
      if (selected.length === 0) { alert('Selectează ceva!'); return; }
      if (confirm('Ștergi ' + selected.length + ' competiții?')) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = '${pageContext.request.contextPath}/DeleteCompetitions';
        selected.forEach(cb => {
          const input = document.createElement('input');
          input.type = 'hidden';
          input.name = 'competition_ids';
          input.value = cb.getAttribute('data-competition-id');
          form.appendChild(input);
        });
        document.body.appendChild(form);
        form.submit();
      }
    });
  </script>
</t:pageTemplate>