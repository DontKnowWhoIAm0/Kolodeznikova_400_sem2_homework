<#include "/WEB-INF/templates/fragments/overlay.ftl">

<div class="sidebar" id="sidebar">
    <div class="menu">
        <a href="${contextPath}/main" class="main_page">Главная</a> <br>
        <a href="${contextPath}/workouts" class="workouts_page">Мои тренировки</a> <br>
        <a href="${contextPath}/requests" class="requests_page">Мои активные запросы</a> <br>
        <a href="${contextPath}/notifications" class="notifications_page">Уведомления</a>
    </div>

    <div class="profile">
        <a href="${contextPath}/profile" class="profile_link">
            <div class="avatar">
                <img src="${user.profileImageUrl!'/images/default_profile.jpg'}"
                     alt="${user.nickname}" class="profile_image">
            </div>
            <div class="userinfo">
                <div class="nickname">${user.nickname}</div>
                <div class="role">Статус/роль</div>
            </div>
        </a>
    </div>
</div>
