<#include "/WEB-INF/templates/main/workout_list.ftl">

<#macro profile>
    <div class="profile_page">
        <div class="info">
            <div class="avatar_block">
                <img src="${user.profileImageUrl!'/images/default_profile.jpg'}"
                     alt="${user.nickname}" class="profile_image">
            </div>

            <div class="user_data">
                <h2 class="nickname">${user.nickname}</h2>
                <p class="fio">${user.name} ${user.lastname}</p>
                <p class="gender">
                    <#if user.gender == "MALE">
                        Мужской
                    <#else>
                        Женский
                    </#if>
                </p>
            </div>

            <#if user.id==userId>
                <form action="${contextPath}/logout" method="post" class="logout_form">
                    <button type="submit" class="logout_button">Выйти</button>
                </form>
            </#if>
        </div>

        <div class="user_workouts">
            <@workout_list />
        </div>
    </div>

    <script>
        const contextPath = "${contextPath}";
    </script>
</#macro>