<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="com.javarush.siberia.model.Role" %>

<%
    List<String> quests = (List<String>)request.getAttribute("quests");
    request.setAttribute("title", "Главная");
%>

<%@ include file="parts/header.jsp" %>

<html>
<head>
    <title>Главная</title>
</head>
<body>

<h1>Добро пожаловать странник</h1>
<h2>Список доступных квестов:</h2>

<% if (!loggedIn) { %>

<% } else { %>
<p>Привет, <b><%=user.getUsername()%></b>! (Роль: <%=user.getRole()%>) <a href="logout">Выйти</a></p>
<% } %>

<ul>

    <% for(String q : quests) { %>
    <li>
        <% if (!loggedIn) { %>
        <%=q%> (чтобы начать - <a href="login">войдите</a>)
        <% } else { %>
        <a href="quest?questId=<%=q%>"><img src="https://ih1.redbubble.net/image.1094644137.8652/st,small,507x507-pad,600x600,f8f8f8.jpg" alt="Quest Logo" width="100" height="100"><%=q%></a>
        <% } %>
    </li>
    <% } %>
</ul>

<p><a href="stats">Посмотреть статистику</a></p>

<% if (loggedIn && (user.getRole().toString().equals("ADMIN") || user.getRole().toString().equals("AUTHOR"))) { %>
<p><a href="office">Личный кабинет (создание квестов)</a></p>
<% } %>

<% if (loggedIn && user.getRole() == Role.ADMIN) { %>
<a class="btn btn-danger mt-3" href="admin">Админ-панель (управление пользователями)</a>
<% } %>

<%@ include file="parts/footer.jsp" %>