<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.javarush.siberia.model.QuestStep"%>

<%
  QuestStep step = (QuestStep)request.getAttribute("step");
  request.setAttribute("title", "Квест");
%>

<%@ include file="parts/header.jsp" %>

<h2 class="mb-4">Квест</h2>
<img src="<%=step.getImagePath()%>" alt="Image" class="img-fluid mb-3">
<p class="fs-5"><%=step.getText()%></p>
<form method="post" action="quest">
  <div class="mb-3">
    <% if (step.getOption1() != null) { %>
    <div class="form-check mb-2">
      <input type="radio" class="form-check-input" name="choice" value="option1" checked>
      <label class="form-check-label"><%=step.getOption1()%></label>
    </div>
    <% } %>
    <% if (step.getOption2() != null) { %>
    <div class="form-check mb-2">
      <input type="radio" class="form-check-input" name="choice" value="option2">
      <label class="form-check-label"><%=step.getOption2()%></label>
    </div>
    <% } %>
  </div>
  <% if (step.isEnd()) { %>
  <p class="text-muted">Конец игры.</p>
  <a class="btn btn-secondary" href="quest?restart=true">Начать заново</a>
  <% } else { %>
  <input type="submit" value="Действие" class="btn btn-primary">
  <% } %>
  <a class="btn btn-light" href="/">На главную</a>
</form>

<%@ include file="parts/footer.jsp" %>