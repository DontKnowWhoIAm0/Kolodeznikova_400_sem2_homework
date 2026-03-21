<!DOCTYPE html>
<html>
<head>
    <title>Мои заметки</title>
</head>
<body>

<h2>Мои заметки</h2>

<#if notes?size == 0>
    <p>У вас нет заметок. <a href="notes/create">Создайте заметку!</a></p>
<#else>
    <ul>
        <#list notes as note>
            <li>
                <h3>${note.title}</h3>
                <p>${note.content}</p>
                <p>Создано: ${note.createdAt}</p>
                <#if note.public>
                    <p>Статус: Публичная</p>
                <#else>
                    <p>Статус: Приватная</p>
                </#if>
                <a href="notes/${note.id}/edit">Редактировать</a> |
                <form method="post" action="notes/${note.id}/delete" style="display:inline;">
                    <button type="submit">Удалить</button>
                </form>
            </li>
        </#list>
    </ul>
</#if>

<a href="notes/create">Создать новую заметку</a>

</body>
</html>