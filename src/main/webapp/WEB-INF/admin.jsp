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
    <thead>
    <tr>
        <th>Имя</th>
        <th>Пароль</th>
        <th>Роль</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <% for (User u : users) { %>
    <tr>
        <td><%=u.getUsername()%></td>
        <td>****</td>
        <td><%=u.getRole()%></td>
        <td>
            <a href="admin?editUsername=<%=u.getUsername()%>"
               class="btn btn-sm btn-primary">Редактировать</a>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<% if (editUser != null) { %>
<h2 class="mt-4">Редактирование пользователя: <%=editUser.getUsername()%></h2>
<form method="post" action="admin">
    <input type="hidden" name="oldUsername" value="<%=editUser.getUsername()%>">

    <div class="mb-3">
        <label>Новое имя пользователя (если оставить пустым - не менять):</label>
        <input type="text" name="newUsername"
               class="form-control" placeholder="<%=editUser.getUsername()%>">
    </div>
    <div class="mb-3">
        <label>Новый пароль (если оставить пустым - не менять):</label>
        <input type="password" name="newPassword" class="form-control">
    </div>
    <div class="mb-3">
        <label>Новая роль (если не выбрать - не менять):</label>
        <select name="newRole" class="form-select">
            <option value="">(не менять)</option>
            <option value="USER">USER</option>
            <option value="AUTHOR">AUTHOR</option>
            <option value="ADMIN">ADMIN</option>
        </select>
    </div>
    <input type="submit" value="Сохранить" class="btn btn-success">
    <a href="admin" class="btn btn-secondary">Отмена</a>
</form>
<% } %>

<a href="/" class="btn btn-light mt-4">На главную</a>

<%@ include file="parts/footer.jsp" %>