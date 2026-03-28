<html>
<head>
    <title>Регистрация</title>
</head>
<body>

<h2>Регистрация</h2>

<#if errorMessage??>
    <div style="color: red;">
        ${errorMessage}
    </div>
</#if>

<form action="/users" method="post">
    <input type="text" name="username" placeholder="Username" required />
    <input type="email" name="email" placeholder="Email" required />
    <input type="password" name="password" placeholder="Password" required />
    <button type="submit">Register</button>
</form>

</body>
</html>