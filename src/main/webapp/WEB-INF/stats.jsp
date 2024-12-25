<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Map.Entry"%>

<%
    Map<String,Integer> stats = (Map<String,Integer>)request.getAttribute("stats");
    request.setAttribute("title", "Статистика игр");
%>
<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Статистика игр</h1>
<table class="table table-bordered table-striped">
    <tr><th>Пользователь</th><th>Сыграно игр</th></tr>
    <% for(Entry<String,Integer> e : stats.entrySet()) { %>
    <tr><td><%=e.getKey()%></td><td><%=e.getValue()%></td></tr>
    <% } %>
</table>
<a class="btn btn-secondary" href="/">На главную</a>

<%@ include file="parts/footer.jsp" %>

