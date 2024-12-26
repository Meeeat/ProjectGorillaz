<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.javarush.siberia.model.User"%>
<%@ page import="com.javarush.siberia.model.Role"%>
<%@ page import="java.util.Collection"%>
<%
    Collection<User> users = (Collection<User>)request.getAttribute("users");
    User editUser = (User)request.getAttribute("editUser");
    String message = (String)request.getAttribute("message");
    String error = (String)request.getAttribute("error");
    request.setAttribute("title", "Админ-панель");
%>
<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Админ-панель</h1>

<% if (message != null) { %>
<div class="alert alert-success"><%=message%></div>
<% } %>
<% if (error != null) { %>
<div class="alert alert-danger"><%=error%></div>
<% } %>

<h2 class="mb-3">Список пользователей</h2>
<table class="table table-striped table-bordered">
    <tr><th>Имя</th>
        <th>Роль</th>
        <th>Действия</th></tr>
    <% for (User u : users) { %>
    <tr>
        <td><%=u.getUsername()%></td>
        <td><%=u.getRole()%></td>
        <td>
            <a href="admin?editUsername=<%=u.getUsername()%>" class="btn btn-sm btn-primary">Редактировать</a>
        </td>
    </tr>
    <% } %>
</table>

<% if (editUser != null) { %>
<h2 class="mt-4">Редактирование пользователя: <%=editUser.getUsername()%></h2>
<form method="post" action="admin">
    <input type="hidden" name="username" value="<%=editUser.getUsername()%>">
    <div class="mb-3">
        <label>Новый пароль (оставьте пустым, чтобы не менять):</label>
        <input type="password" name="newPassword" class="form-control">
    </div>
    <div class="mb-3">
        <label>Новая роль:</label>
        <select name="newRole" class="form-select">
            <option value="">(не менять)</option>
            <option value="USER" <%= editUser.getRole() == Role.USER ? "selected" : "" %>>USER</option>
            <option value="AUTHOR" <%= editUser.getRole() == Role.AUTHOR ? "selected" : "" %>>AUTHOR</option>
            <option value="ADMIN" <%= editUser.getRole() == Role.ADMIN ? "selected" : "" %>>ADMIN</option>
        </select>
    </div>
    <input type="submit" value="Сохранить" class="btn btn-success">
    <a href="admin" class="btn btn-secondary">Отмена</a>
</form>
<% } %>

<a href="/" class="btn btn-light mt-4">На главную</a>

<%@ include file="parts/footer.jsp" %>
