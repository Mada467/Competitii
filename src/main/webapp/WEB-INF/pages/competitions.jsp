<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:pageTemplate pageTitle="Lista Competiții">
  <div class="mb-4">
    <h1 class="fw-bold text-dark">Competiții CSEE</h1>
  </div>

  <%-- Mesaje de feedback din URL (Status messages) --%>
  <c:choose>
    <c:when test="${param.withdrawn == 'true'}">
      <div class="alert alert-warning alert-dismissible fade show shadow-sm border-warning" role="alert">
        <i class="bi bi-info-circle-fill me-2"></i> Te-ai retras cu succes din competiție!
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    </c:when>
    <c:when test="${param.applied == 'true'}">
      <div class="alert alert-success alert-dismissible fade show shadow-sm border-success" role="alert">
        <i class="bi bi-check-circle-fill me-2"></i> Aplicația ta a fost trimisă cu succes!
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    </c:when>
    <c:when test="${not empty param.error}">
      <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
        <i class="bi bi-exclamation-octagon-fill me-2"></i> Eroare: ${param.error}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
      </div>
    </c:when>
  </c:choose>

  <%-- Zona de Căutare --%>
  <div class="card mb-4 shadow-sm border-0 bg-light">
    <div class="card-body">
      <form method="GET" action="${pageContext.request.contextPath}/Competitions" class="row g-2">
        <div class="col-md-10">
          <div class="input-group">
            <span class="input-group-text bg-white border-end-0"><i class="bi bi-search"></i></span>
            <input type="text" name="search" class="form-control border-start-0"
                   placeholder="Caută după nume sau descriere..." value="${searchKeyword}">
          </div>
        </div>
        <div class="col-md-2">
          <button type="submit" class="btn btn-dark w-100 fw-bold">Filtrează</button>
        </div>
      </form>
    </div>
  </div>

  <div class="list-group shadow-sm mb-4">
    <c:forEach var="comp" items="${competitions}">
      <div class="list-group-item list-group-item-action p-4 border-start border-4 ${comp.status == 'OPEN' ? 'border-success' : 'border-secondary'}">
        <div class="row align-items-center">

            <%-- Selectare multiplă pentru Reprezentant --%>
          <c:if test="${sessionScope.user.role == 'REPRESENTATIVE'}">
            <div class="col-auto">
              <input class="form-check-input delete-checkbox border-secondary" type="checkbox"
                     data-competition-id="${comp.id}" style="width: 20px; height: 20px; cursor: pointer;">
            </div>
          </c:if>

          <div class="col">
            <div class="d-flex w-100 justify-content-between align-items-start">
              <h5 class="mb-1 text-dark fw-bold">${comp.name}</h5>
              <div class="text-end">
                <small class="d-block text-muted">
                  <i class="bi bi-calendar-event"></i> Deadline:
                </small>
                <span class="badge bg-light text-danger border border-danger-subtle">
                  <fmt:parseDate value="${comp.applicationDeadline}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                  <fmt:formatDate value="${parsedDate}" pattern="dd MMM yyyy, HH:mm" />
                </span>
              </div>
            </div>

            <p class="mb-2 text-muted text-truncate" style="max-width: 80%;">${comp.description}</p>

            <div class="d-flex gap-2 align-items-center">
              <span class="badge rounded-pill bg-primary-subtle text-primary border border-primary-subtle">
                <i class="bi bi-people-fill"></i> Echipa: ${comp.minParticipants}-${comp.maxParticipants}
              </span>
              <c:choose>
                <c:when test="${comp.status == 'OPEN'}">
                  <span class="badge rounded-pill bg-success-subtle text-success border border-success-subtle">DESCHISĂ</span>
                </c:when>
                <c:otherwise>
                  <span class="badge rounded-pill bg-secondary-subtle text-secondary border border-secondary-subtle">${comp.status}</span>
                </c:otherwise>
              </c:choose>
              <c:if test="${comp.internal}">
                <span class="badge rounded-pill bg-warning-subtle text-dark border border-warning-subtle">
                  <i class="bi bi-shield-lock"></i> Doar UPT
                </span>
              </c:if>
            </div>
          </div>

          <div class="col-auto text-end border-start ps-4">
              <%-- Acțiuni Reprezentant --%>
            <c:if test="${sessionScope.user.role == 'REPRESENTATIVE'}">
              <div class="btn-group-vertical w-100">
                <a href="${pageContext.request.contextPath}/ViewApplications?id=${comp.id}"
                   class="btn btn-sm btn-info fw-bold mb-1">Înscrieri</a>
                <a href="${pageContext.request.contextPath}/EditCompetition?id=${comp.id}"
                   class="btn btn-sm btn-outline-secondary">Editează</a>
              </div>
            </c:if>

              <%-- Acțiuni Student / Elev --%>
            <c:if test="${sessionScope.user.role == 'STUDENT' || sessionScope.user.role == 'ELEV'}">
              <c:set var="appStatus" value="${applicationStatus[comp.id]}" />

              <c:choose>
                <%-- CAZUL 1: A fost APROBAT --%>
                <c:when test="${appStatus == 'ACCEPTED'}">
                  <div class="text-center">
                    <span class="badge bg-success px-3 py-2 mb-2">
                      <i class="bi bi-check-circle-fill"></i> APROBAT
                    </span>
                    <form action="${pageContext.request.contextPath}/WithdrawFromCompetition" method="POST"
                          onsubmit="return confirm('Ești sigur că vrei să te retragi?');">
                      <input type="hidden" name="competition_id" value="${comp.id}">
                      <button type="submit" class="btn btn-link btn-sm text-danger text-decoration-none">
                        Retrage-te
                      </button>
                    </form>
                  </div>
                </c:when>

                <%-- CAZUL 2: A fost RESPINS - NU poate aplica din nou --%>
                <c:when test="${appStatus == 'REJECTED'}">
                  <div class="text-center">
                    <span class="badge bg-danger px-3 py-2">
                      <i class="bi bi-x-circle-fill"></i> RESPINS
                    </span>
                    <small class="d-block text-muted mt-1">Nu poți aplica din nou</small>
                  </div>
                </c:when>

                <%-- CAZUL 3: Aplicația este ÎN AȘTEPTARE --%>
                <c:when test="${appStatus == 'PENDING'}">
                  <div class="text-center">
                    <span class="badge bg-warning text-dark px-3 py-2 mb-2">
                      <i class="bi bi-clock-history"></i> ÎN AȘTEPTARE
                    </span>
                    <form action="${pageContext.request.contextPath}/WithdrawFromCompetition" method="POST"
                          onsubmit="return confirm('Ești sigur că vrei să anulezi aplicația?');">
                      <input type="hidden" name="competition_id" value="${comp.id}">
                      <button type="submit" class="btn btn-link btn-sm text-danger text-decoration-none">
                        Retrage aplicația
                      </button>
                    </form>
                  </div>
                </c:when>

                <%-- CAZUL 4: NU a aplicat încă - poate aplica --%>
                <c:when test="${comp.status == 'OPEN' && appStatus == null}">
                  <button type="button" class="btn btn-success px-4 fw-bold shadow-sm"
                          data-bs-toggle="modal" data-bs-target="#applyModal${comp.id}">
                    Aplică
                  </button>
                </c:when>

                <%-- CAZUL 5: Competiția este închisă --%>
                <c:otherwise>
                  <span class="badge bg-secondary px-3 py-2">Închisă</span>
                </c:otherwise>
              </c:choose>
            </c:if>
          </div>
        </div>
      </div>

      <%-- Modal Înscriere --%>
      <div class="modal fade" id="applyModal${comp.id}" data-bs-backdrop="static" tabindex="-1">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content border-0 shadow">
            <form action="${pageContext.request.contextPath}/ApplyToCompetition" method="POST">
              <input type="hidden" name="competition_id" value="${comp.id}">
              <div class="modal-header bg-success text-white">
                <h5 class="modal-title fw-bold">Înscriere: ${comp.name}</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
              </div>
              <div class="modal-body p-4">
                <p class="text-muted small mb-3">Te rugăm să introduci detaliile necesare pentru participare (ex. echipa, link portofoliu).</p>
                <label class="form-label fw-bold">Informații adiționale:</label>
                <textarea name="additional_info" class="form-control" rows="3" required placeholder="Scrie aici..."></textarea>
              </div>
              <div class="modal-footer bg-light">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Anulează</button>
                <button type="submit" class="btn btn-success px-4 fw-bold">Trimite Aplicația</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>

  <c:if test="${not empty competitions && sessionScope.user.role == 'REPRESENTATIVE'}">
    <div class="mt-3">
      <button type="button" class="btn btn-danger btn-lg shadow-sm fw-bold" id="btnDelete">
        <i class="bi bi-trash3-fill"></i> Șterge Selecția
      </button>
    </div>
  </c:if>

  <script>
    document.getElementById('btnDelete')?.addEventListener('click', function() {
      const selected = document.querySelectorAll('.delete-checkbox:checked');
      if (selected.length === 0) {
        alert('Vă rugăm să selectați cel puțin o competiție pentru a fi ștearsă.');
        return;
      }
      if (confirm('Atenție! Veți șterge ' + selected.length + ' competiții definitiv. Continuați?')) {
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