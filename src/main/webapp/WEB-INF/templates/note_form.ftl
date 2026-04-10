<html>
<head>
    <title>Создать/Редактировать заметку</title>
</head>
<body>

<h2>
    <#if note.id??>
        Редактировать заметку
    <#else>
        Создать новую заметку
    </#if>
</h2>

<form method="post"
      action="
          <#if note.id??>
              edit
          <#else>
              create
          </#if>
      ">

    <div>
        <label for="title">Заголовок:</label>
        <input type="text" id="title" name="title" value="${note.title!''}" required/>
    </div>

    <div>
        <label for="content">Содержание:</label>
        <textarea id="content" name="content" required>${note.content!''}</textarea>
    </div>

    <div>
        <label>Публичная заметка:</label><br/>
        <label>
            <input type="radio" name="public" value="true"
                   <#if note.public?? && note.public == true>checked</#if>/> Да
        </label>
        <label>
            <input type="radio" name="public" value="false"
                   <#if note.public?? && !note.public>checked</#if>/> Нет
        </label>
    </div>

    <button type="submit">
        <#if note.id??>
            Сохранить
        <#else>
            Создать
        </#if>
    </button>

</form>

</body>
</html>