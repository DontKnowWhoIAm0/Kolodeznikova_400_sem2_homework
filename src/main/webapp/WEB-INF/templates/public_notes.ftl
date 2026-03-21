<!DOCTYPE html>
<html>
<head>
    <title>Публичные заметки</title>
</head>
<body>

<h2>Публичные заметки</h2>

<#if notes?size == 0>
    <p>Публичных заметок нет.</p>
<#else>
    <ul>
        <#list notes as note>
            <li>
                <h3>${note.title}</h3>
                <p>${note.content}</p>
                <p>Автор: ${note.author.username}</p>
                <p>Создано: ${note.createdAt}</p>
                <p>Статус: Публичная</p>
            </li>
        </#list>
    </ul>
</#if>

</body>
</html>