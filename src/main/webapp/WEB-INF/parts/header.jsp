<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.String" %>

<%
  String pageTitle = (String)request.getAttribute("title");
  if (pageTitle == null) {
    pageTitle = "Мой квест";
  }
%>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><%= pageTitle %></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body {
      padding-top: 3rem;
      background: #f8f9fa;
    }
    .container {
      max-width: 700px;
    }
  </style>
</head>
<body>
<div class="container">