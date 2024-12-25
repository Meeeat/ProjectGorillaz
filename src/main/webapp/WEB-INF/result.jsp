<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.javarush.siberia.model.QuestStep"%>

<%
    QuestStep step = (QuestStep)request.getAttribute("step");
    request.setAttribute("title", "Результат");
%>
<%@ include file="parts/header.jsp" %>

<h2 class="mb-4">Результат</h2>
<p class="fs-5"><%=step.getText()%></p>
<img src="<%=step.getImagePath()%>" class="img-fluid mb-3">
<div class="mb-3">
    <a class="btn btn-secondary" href="quest?restart=true">Начать заново</a>
    <a class="btn btn-light" href="/">Вернуться на главную</a>
</div>

<%@ include file="parts/footer.jsp" %>
