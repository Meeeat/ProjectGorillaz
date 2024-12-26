<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map"%>
<%@ page import="com.javarush.siberia.model.Stats"%>

<%
    Map<String, Stats> allStats = (Map<String, Stats>)request.getAttribute("allStats");
    request.setAttribute("title", "Статистика игр");
%>
<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Статистика игр</h1>
<table class="table table-bordered table-striped">
    <thead>
    <tr>
        <th>Пользователь</th>
        <th>Всего игр</th>
        <th>Побед</th>
        <th>Поражений</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (allStats != null) {
            for (Map.Entry<String, Stats> entry : allStats.entrySet()) {
                String username = entry.getKey();
                Stats stats = entry.getValue();
    %>
    <tr>
        <td><%= username %></td>
        <td><%= stats.getTotal() %></td>
        <td><%= stats.getWins() %></td>
        <td><%= stats.getLosses() %></td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
<a class="btn btn-secondary" href="/">На главную</a>

<%@ include file="parts/footer.jsp" %>

