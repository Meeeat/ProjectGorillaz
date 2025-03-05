<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
  String message = (String) request.getAttribute("message");
  request.setAttribute("title", "Личный кабинет");
%>

<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Личный кабинет</h1>

<h2>Создать новый квест</h2>
<form method="post" action="office" class="mb-4">
  <input type="hidden" name="action" value="createQuest">
  <div class="mb-3">
    <label>Quest ID:</label>
    <input type="text" name="questId" class="form-control" placeholder="Например: myFirstQuest">
  </div>
  <input type="submit" value="Создать квест" class="btn btn-primary">
</form>

<h2>Добавить шаг к квесту</h2>
<form method="post" action="office" id="addStepForm">
  <input type="hidden" name="action" value="addStep">

  <div class="mb-3">
    <label>Quest ID:</label>
    <input type="text" name="questId" class="form-control" placeholder="Тот же ID, что вы указали при создании">
  </div>
  <div class="mb-3">
    <label>Step ID:</label>
    <input type="text" name="stepId" class="form-control" placeholder="Например: start, north, caveEntrance...">
  </div>
  <div class="mb-3">
    <label>Text:</label>
    <input type="text" name="text" class="form-control" placeholder="Описание ситуации или комнаты...">
  </div>
  <div class="mb-3">
    <label>Image Path:</label>
    <input type="text" name="imagePath" class="form-control" placeholder="Например: images/step1.jpg">
  </div>

  <div id="optionsContainer">
    <div class="option-group mb-3">
      <label>Option 1:</label>
      <input type="text" name="option_1" class="form-control" placeholder="Например: Пойти на север">
      <label>Next step if Option 1:</label>
      <input type="text" name="next_1" class="form-control" placeholder="Укажите ID шага, куда перейти">
    </div>
  </div>

  <button type="button" id="addOptionButton" class="btn btn-secondary mb-3">Добавить вариант</button>

  <div class="form-check mb-2">
    <input type="checkbox" name="end" class="form-check-input">
    <label class="form-check-label">End game</label>
  </div>
  <div class="form-check mb-3">
    <input type="checkbox" name="victory" class="form-check-input">
    <label class="form-check-label">Victory</label>
  </div>

  <input type="submit" value="Добавить шаг" class="btn btn-primary">
</form>

<% if (message != null) { %>
<div class="alert alert-success mt-3"><%= message %></div>
<% } %>

<a href="/" class="btn btn-secondary">На главную</a>

<script>
  let optionCounter = 1;

  document.getElementById('addOptionButton').addEventListener('click', function() {
    optionCounter++;
    const optionsContainer = document.getElementById('optionsContainer');

    const newOptionGroup = document.createElement('div');
    newOptionGroup.className = 'option-group mb-3';

    newOptionGroup.innerHTML = `
            <label>Option ${optionCounter}:</label>
            <input type="text" name="option_${optionCounter}" class="form-control" placeholder="Например: Пойти на север">
            <label>Next step if Option ${optionCounter}:</label>
            <input type="text" name="next_${optionCounter}" class="form-control" placeholder="Укажите ID шага, куда перейти">
        `;

    optionsContainer.appendChild(newOptionGroup);
  });
</script>

<%@ include file="parts/footer.jsp" %>