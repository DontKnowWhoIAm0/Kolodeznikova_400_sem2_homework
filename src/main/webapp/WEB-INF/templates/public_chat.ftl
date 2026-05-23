<html>
<head>
    <title>Публичный чат</title>
</head>
<body>

<h2>История сообщений (публичный просмотр)</h2>

<#if currentUsername??>
    <p>Вы вошли как <strong>${currentUsername}</strong>. <a href="/chat">Перейти в чат</a></p>
<#else>
    <p><a href="/login">Войдите</a>, чтобы участвовать в чате.</p>
</#if>

<#if messages?size == 0>
    <p>Сообщений пока нет.</p>
<#else>
    <ul>
        <#list messages?reverse as msg>
            <li>
                <strong>${msg.authorUsername}</strong>
                <small>(${msg.sentAt})</small>:
                ${msg.content}
            </li>
        </#list>
    </ul>
</#if>

</body>
</html>