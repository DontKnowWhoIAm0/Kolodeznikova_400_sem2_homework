<div class="responded_item">
    <div class="profile_info">
        <img src="${respondent.profileImageUrl}" alt="${respondent.nickname}" class="profile_image">
        <div class="name_nickname">
            <p class="nickname">${respondent.nickname}</p>
            <p class="full_name">${respondent.name} ${respondent.lastname}</p>
            <p class="gender">
                <#if respondent.gender == "MALE">
                    Мужской
                <#else>
                    Женский
                </#if>
            </p>
        </div>
    </div>

    <div class="action_buttons">
        <button class="cancel_button">Отменить</button>
        <button class="confirm_button">Подтвердить</button>
    </div>
</div>