<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.javarush.siberia.model.User" %>


<%
  String pageTitle = (String)request.getAttribute("title");
  if (pageTitle == null) {
    pageTitle = "Quest room";
  }
%>

<%
  User user = (User) session.getAttribute("user");
  boolean loggedIn = (user != null);
%>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><%= pageTitle %></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

  <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
    <div class="container-fluid">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link" href="/">Главная</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/index">Все квесты</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/stats">Статистика</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="/office">Личный кабинет</a>
          </li>
        </ul>
        <ul class="navbar-nav">
          <% if (loggedIn) { %>
          <li class="nav-item">
            <span class="navbar-text me-3">Привет, <b><%= user.getUsername() %></b></span>
          </li>
          <li class="nav-item">
            <a class="btn btn-outline-danger" href="/logout">Выйти</a>
          </li>
          <% } else { %>
          <li class="nav-item">
            <a class="btn btn-outline-primary me-2" href="/login">Войти</a>
          </li>
          <li class="nav-item">
            <a class="btn btn-outline-success" href="/register">Зарегистрироваться</a>
          </li>
          <% } %>
        </ul>
      </div>
    </div>
  </nav>

  <style>
    body {
      background-image: url('https://i.pinimg.com/originals/e8/c9/df/e8c9df3391c56187e8ebb909f33da143.jpg');
      background-size: cover;
      background-repeat: no-repeat;
      background-attachment: fixed;
      background-position: center;

      padding-top: 3rem;

    }
    .container {
      max-width: 800px;
    }
  </style>
</head>
<body>
<div class="container">