<#macro signupForm>
    <div> <h2 style="text-align: center;">Регистрация</h2></div>

    <#if error??>
        <div class="error">${error}</div>
    </#if>

    <form method="post" action="signup" enctype="multipart/form-data">

        <input type="text" id="login" name="login" placeholder="Логин" required>
        <div id="login-error" class="error"></div>

        <input type="password" id="password" name="password" placeholder="Пароль" required>
        <div id="password-error" class="error"></div>

        <input type="text" name="name" placeholder="Имя" required>
        <input type="text" name="lastname" placeholder="Фамилия" required>

        <input type="text" id="nickname" name="nickname" placeholder="Никнейм" required>
        <div id="nickname-error" class="error"></div>

        <select name="gender" required>
            <option value="" disabled selected>Пол</option>
            <option value="MALE">Мужской</option>
            <option value="FEMALE">Женский</option>
        </select>

        <select name="wayOfCommunication" required>
            <option value="" disabled selected>Способ связи</option>
            <option value="Telegram">Telegram</option>
            <option value="WhatsApp">WhatsApp</option>
            <option value="Vk">Vk</option>
            <option value="Email">Email</option>
            <option value="SMS">SMS</option>
        </select>

        <input type="text" name="contactValue" placeholder="Введите номер телефона или юзернейм" required>

        <label>Фото профиля:</label>
        <input type="file" name="profile_image" accept="image/*">

        <button type="submit" id="signup_button">Зарегистрироваться</button>
    </form>

    <div class="login_link">
        <p>Уже есть аккаунт? <a href="login"> Войти</a></p>
    </div>
</#macro>