<html>
<head>
    <title>Мои сообщения</title>
</head>
<body>

<h2>Мои сообщения</h2>

<p><a href="/chat">Вернуться в чат</a></p>

<#if messages?size == 0>
    <p>Вы ещё не отправляли сообщений.</p>
<#else>
    <ul>
        <#list messages as msg>
            <li>
                <small>(${msg.sentAt})</small>
                ${msg.content}
                <form method="post" action="/chat/${msg.id}/delete">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit">Удалить</button>
                </form>
            </li>
        </#list>
    </ul>
</#if>

</body>
</html>