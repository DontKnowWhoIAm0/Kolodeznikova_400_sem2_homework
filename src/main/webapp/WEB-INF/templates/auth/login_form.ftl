<#macro loginForm>
    <h2 style="text-align: center;">Авторизация</h2>

    <#if error??>
        <div class="error">${error}</div>
    </#if>

    <form method="post" action="login">
        <input type="text" name="login" placeholder="Логин" required>
        <input type="password" name="password" placeholder="Пароль" required>

        <button type="submit">Авторизоваться</button>
    </form>

    <div class="login_link">
        <p>Нет аккаунта? <a href="signup">Зарегистрироваться</a></p>
    </div>
</#macro>