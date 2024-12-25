<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  String error = (String)request.getAttribute("error");
  request.setAttribute("title", "Вход");
%>

<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Вход</h1>
<form method="post" action="login" class="mb-3">
  <div class="mb-3">
    <label>Имя пользователя:</label>
    <input type="text" name="username" class="form-control">
  </div>
  <div class="mb-3">
    <label>Пароль:</label>
    <input type="password" name="password" class="form-control">
  </div>
  <input type="submit" value="Войти" class="btn btn-primary">
</form>
<% if (error != null) { %>
<div class="alert alert-danger"><%=error%></div>
<% } %>
<a href="/" class="btn btn-secondary">На главную</a>

<%@ include file="parts/footer.jsp" %>