<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  String message = (String)request.getAttribute("message");
  request.setAttribute("title", "Личный кабинет");
%>

<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Личный кабинет</h1>
<h2>Создать новый квест</h2>
<form method="post" action="office" class="mb-4">
  <input type="hidden" name="action" value="createQuest">
  <div class="mb-3">
    <label>Quest ID:</label>
    <input type="text" name="questId" class="form-control">
  </div>
  <input type="submit" value="Создать квест" class="btn btn-primary">
</form>

<h2>Добавить шаг к квесту</h2>
<form method="post" action="office">
  <input type="hidden" name="action" value="addStep">
  <div class="mb-3">
    <label>Quest ID:</label>
    <input type="text" name="questId" class="form-control">
  </div>
  <div class="mb-3">
    <label>Step ID:</label>
    <input type="text" name="stepId" class="form-control">
  </div>
  <div class="mb-3">
    <label>Text:</label>
    <input type="text" name="text" class="form-control">
  </div>
  <div class="mb-3">
    <label>Image Path:</label>
    <input type="text" name="imagePath" class="form-control">
  </div>
  <div class="mb-3">
    <label>Option 1:</label>
    <input type="text" name="option1" class="form-control">
  </div>
  <div class="mb-3">
    <label>Option 2:</label>
    <input type="text" name="option2" class="form-control">
  </div>
  <div class="mb-3">
    <label>Next step if option1:</label>
    <input type="text" name="next1" class="form-control">
  </div>
  <div class="mb-3">
    <label>Next step if option2:</label>
    <input type="text" name="next2" class="form-control">
  </div>
  <div class="form-check mb-2">
    <input type="checkbox" name="end" class="form-check-input">
    <label class="form-check-label">End game</label>
  </div>
  <div class="form-check mb-3">
    <input type="checkbox" name="victory" class="form-check-input">
    <label class="form-check-label">Victory</label>
  </div>
  <input type="submit" value="Добавить шаг" class="btn btn-primary">
</form>

<% if (message != null) { %>
<div class="alert alert-success mt-3"><%=message%></div>
<% } %>

<a href="/" class="btn btn-secondary">На главную</a>

<%@ include file="parts/footer.jsp" %>

