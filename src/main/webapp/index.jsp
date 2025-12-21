<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Acasă">
  <%-- Hero Section: Impact Vizual --%>
  <div class="row align-items-center py-5">
    <div class="col-lg-6">
      <h1 class="display-3 fw-bold text-dark mb-4">
        Excelența începe prin <span class="text-primary">Competiție</span>.
      </h1>
      <p class="lead text-muted mb-4 shadow-sm p-3 bg-light border-start border-4 border-warning">
        Participă la cele mai importante evenimente tehnice organizate de Departamentul
        <strong>Calculatoare și Inginerie Electrică (CSEE)</strong> din cadrul ULBS.
      </p>

      <div class="d-grid gap-2 d-md-flex justify-content-md-start">
        <a href="${pageContext.request.contextPath}/Competitions" class="btn btn-primary btn-lg px-4 me-md-2 fw-bold shadow">
          <i class="bi bi-rocket-takeoff-fill me-2"></i>Vezi Competiții
        </a>
        <c:if test="${empty sessionScope.user}">
          <a href="${pageContext.request.contextPath}/Register" class="btn btn-outline-dark btn-lg px-4">
            Creează Cont
          </a>
        </c:if>
      </div>
    </div>
    <div class="col-lg-6 d-none d-lg-block text-center">
      <i class="bi bi-cpu text-primary opacity-25" style="font-size: 15rem;"></i>
    </div>
  </div>

  <%-- Secțiune Caracteristici (Cards) --%>
  <div class="row g-4 py-5 border-top mt-5">
    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm transition-hover">
        <div class="card-body text-center p-4">
          <div class="feature-icon bg-primary bg-gradient text-white fs-2 mb-3 rounded-circle mx-auto d-flex align-items-center justify-content-center" style="width: 70px; height: 70px;">
            <i class="bi bi-laptop"></i>
          </div>
          <h4 class="fw-bold">Hackathoane</h4>
          <p class="text-muted small">Dezvoltă soluții software inovatoare în timp record alături de echipa ta.</p>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm transition-hover">
        <div class="card-body text-center p-4">
          <div class="feature-icon bg-success bg-gradient text-white fs-2 mb-3 rounded-circle mx-auto d-flex align-items-center justify-content-center" style="width: 70px; height: 70px;">
            <i class="bi bi-robot"></i>
          </div>
          <h4 class="fw-bold">Robotică</h4>
          <p class="text-muted small">Testează-ți abilitățile de hardware și algoritmică în competiții de profil.</p>
        </div>
      </div>
    </div>
    <div class="col-md-4">
      <div class="card h-100 border-0 shadow-sm transition-hover">
        <div class="card-body text-center p-4">
          <div class="feature-icon bg-warning bg-gradient text-dark fs-2 mb-3 rounded-circle mx-auto d-flex align-items-center justify-content-center" style="width: 70px; height: 70px;">
            <i class="bi bi-award"></i>
          </div>
          <h4 class="fw-bold">Certificări</h4>
          <p class="text-muted small">Câștigă recunoaștere oficială și premii din partea partenerilor noștri industriali.</p>
        </div>
      </div>
    </div>
  </div>

  <%-- Call to Action: Logare --%>
  <c:if test="${empty sessionScope.user}">
    <div class="bg-dark text-white rounded-4 p-5 text-center mt-5 shadow">
      <h2 class="fw-bold mb-3">Ești gata să te înscrii?</h2>
      <p class="mb-4 opacity-75">Autentifică-te pentru a avea acces la formularul de înscriere și detalii suplimentare.</p>
      <a href="${pageContext.request.contextPath}/Login" class="btn btn-warning btn-lg px-5 fw-bold text-dark">
        Intră în Cont
      </a>
    </div>
  </c:if>
</t:pageTemplate>