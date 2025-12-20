<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:pageTemplate pageTitle="Competitii - Home">
  <div class="py-5 text-center">
    <h1 class="display-5 fw-bold text-body-emphasis">Bine ai venit la Competitii!</h1>
    <div class="col-lg-6 mx-auto">
      <p class="lead mb-4">Aceasta este o platformă modernă pentru gestionarea concursurilor academice. Proiect realizat conform Laboratorului 2.</p>
      <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
        <a href="${pageContext.request.contextPath}/about.jsp" class="btn btn-primary btn-lg px-4 gap-3">Află mai multe</a>
      </div>
    </div>
  </div>
</t:pageTemplate>