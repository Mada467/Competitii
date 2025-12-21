<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Despre Proiect">
  <div class="row justify-content-center">
    <div class="col-md-10">
      <div class="text-center mb-5">
        <h1 class="fw-bold display-5">Management Competiții CSEE</h1>
        <p class="lead text-muted">O soluție modernă pentru coordonarea evenimentelor studențești.</p>
        <div class="mx-auto" style="width: 60px; height: 5px; background-color: #ffc107; border-radius: 2px;"></div>
      </div>

      <div class="row g-4 mb-5">
        <div class="col-md-6">
          <div class="card h-100 border-0 shadow-sm p-4">
            <h4 class="fw-bold text-primary"><i class="bi bi-target me-2"></i>Scopul Proiectului</h4>
            <p class="text-secondary mt-3">
              Acest proiect a fost dezvoltat ca parte a laboratorului de <strong>Tehnologii Java (Java EE)</strong>,
              având ca obiectiv principal digitalizarea procesului de înscriere la competițiile organizate în cadrul facultății.
            </p>
            <ul class="list-unstyled text-secondary">
              <li><i class="bi bi-check2-circle text-success me-2"></i> Gestiune centralizată a aplicațiilor.</li>
              <li><i class="bi bi-check2-circle text-success me-2"></i> Validare automată pe baza domeniului de email.</li>
              <li><i class="bi bi-check2-circle text-success me-2"></i> Flux de aprobare pentru reprezentanți.</li>
            </ul>
          </div>
        </div>

        <div class="col-md-6">
          <div class="card h-100 border-0 shadow-sm p-4 bg-dark text-white">
            <h4 class="fw-bold text-warning"><i class="bi bi-cpu me-2"></i>Stack Tehnologic</h4>
            <div class="d-flex flex-wrap gap-2 mt-3">
              <span class="badge bg-secondary">Java Jakarta EE</span>
              <span class="badge bg-secondary">EJB (Enterprise Java Beans)</span>
              <span class="badge bg-secondary">JPA / Hibernate</span>
              <span class="badge bg-secondary">Servlets & JSP</span>
              <span class="badge bg-secondary">MySQL / Derby</span>
              <span class="badge bg-secondary">Bootstrap 5</span>
            </div>
            <p class="small text-light opacity-75 mt-4">
              Arhitectura urmează modelul <strong>MVC (Model-View-Controller)</strong> pentru a asigura
              o separare clară între logica de business și interfața utilizator.
            </p>
          </div>
        </div>
      </div>



      <div class="text-center border-top pt-4">
        <p class="text-muted mb-4 italic">Dezvoltat cu <i class="bi bi-heart-fill text-danger"></i> pentru comunitatea CSEE Sibiu.</p>
        <a href="${pageContext.request.contextPath}/" class="btn btn-outline-primary px-4 rounded-pill">
          <i class="bi bi-house-door me-2"></i>Înapoi la Pagina Principală
        </a>
      </div>
    </div>
  </div>
</t:pageTemplate>