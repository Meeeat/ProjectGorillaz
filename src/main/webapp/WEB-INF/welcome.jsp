<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.javarush.siberia.model.User"%>

<!DOCTYPE html>
<html>

<head>
    <title>Предыстория</title>
</head>
<body>

<h2>Предыстория вашего путешествия</h2>
<p>Вы - отважный герой, который очутился в темном лесу...</p>

<%
    User user = (User)session.getAttribute("user");
    if (user != null) {
%>

<p>Здравствуйте, <b><%=user.getUsername()%></b>! Ваша роль: <%=user.getRole()%></p>
<% } %>
<p><a href="quest">Начать квест</a></p>
<% if (user != null && user.getRole().toString().equals("ADMIN")) { %>
<p><a href="admin">Админ-панель</a></p>
<% } %>
<% if (user != null && user.getRole().toString().equals("AUTHOR")) { %>
<p><a href="author">Автор-панель</a></p>
<% } %>
<p><a href="logout">Выйти</a></p>

</body>
</html>