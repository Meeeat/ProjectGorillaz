<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  String message = (String) request.getAttribute("message");
  request.setAttribute("title", "Личный кабинет");
%>

<%@ include file="parts/header.jsp" %>

<h1 class="mb-4">Личный кабинет</h1>

<h2>Создать новый квест</h2>

<div class="alert alert-info" role="alert">
  <strong>Подсказка:</strong> Здесь вы создаёте <em>новый</em> квест.
  <ul>
    <li><b>Quest ID</b> — это уникальное имя вашего квеста. К примеру, <code>dragonQuest</code> или <code>spaceAdventure</code>.</li>
    <li>Используйте <b>тот же Quest ID</b> при добавлении шагов к этому квесту.</li>
  </ul>
</div>

<form method="post" action="office" class="mb-4">
  <input type="hidden" name="action" value="createQuest">
  <div class="mb-3">
    <label>Quest ID:</label>
    <input type="text" name="questId" class="form-control" placeholder="Например: myFirstQuest">
  </div>
  <input type="submit" value="Создать квест" class="btn btn-primary">
</form>

<h2>Добавить шаг к квесту</h2>

<div class="alert alert-info" role="alert">
  <strong>Подсказка:</strong>
  <ul>
    <li><b>Quest ID</b> — тот же, что вы указали выше при создании квеста.</li>
    <li><b>Step ID</b> — уникальное название шага. Для <em>первого</em> шага квеста используйте <code>start</code>.
      Примеры: <code>start</code>, <code>north</code>, <code>caveEntrance</code>, <code>finalRoom</code> и т.п.
    </li>
    <li><b>Text</b> — описание происходящего на шаге. Показывается игроку.</li>
    <li><b>Image Path</b> — путь к изображению (например, <code>images/step1.jpg</code>), если оно есть в папке <code>webapp/images</code>.</li>
    <li><b>Option 1 / Option 2</b> — названия двух вариантов действий.
      <ul>
        <li><code>Next step if option1</code> и <code>Next step if option2</code> — это ID шагов, куда попадёт игрок при выборе варианта.</li>
      </ul>
    </li>
    <li><b>End game</b> (чекбокс) — означает, что данный шаг является концом игры.</li>
    <li><b>Victory</b> (чекбокс) — означает, что это финал с победой. Если <code>End</code> включён, но <code>Victory</code> нет, значит это поражение или просто завершение.</li>
  </ul>
</div>

<form method="post" action="office">
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
  <div class="mb-3">
    <label>Option 1:</label>
    <input type="text" name="option1" class="form-control" placeholder="Например: Пойти на север">
  </div>
  <div class="mb-3">
    <label>Option 2:</label>
    <input type="text" name="option2" class="form-control" placeholder="Например: Пойти на юг">
  </div>
  <div class="mb-3">
    <label>Next step if option1:</label>
    <input type="text" name="next1" class="form-control" placeholder="Укажите ID шагa, куда перейти при выборе Option1">
  </div>
  <div class="mb-3">
    <label>Next step if option2:</label>
    <input type="text" name="next2" class="form-control" placeholder="Укажите ID шагa, куда перейти при выборе Option2">
  </div>
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

<%@ include file="parts/footer.jsp" %>