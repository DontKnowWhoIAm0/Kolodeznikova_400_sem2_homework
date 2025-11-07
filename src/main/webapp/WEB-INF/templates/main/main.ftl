<#assign pageCss=["css/fragments/sidebar.css",
"css/user_requests_list.css",
"css/fragments/notifications.css",
"css/fragments/header.css",
"css/request_item.css",
"css/request_list.css",
"css/main.css",
"css/fragments/delete_request_confirmation.css",
"css/fragments/overlay.css"]>

<#include "/WEB-INF/templates/main/request_list.ftl">
<#include "/WEB-INF/templates/main/user_request_lists.ftl">
<#include "/WEB-INF/templates/main/workout_list.ftl">

<#macro content>

    <#include "/WEB-INF/templates/fragments/header.ftl">
    <#include "/WEB-INF/templates/fragments/sidebar.ftl">
    <#include "/WEB-INF/templates/fragments/notifications.ftl">
    <#include "/WEB-INF/templates/fragments/delete_request_confirmation.ftl">

    <div class="auth_container">
        <#if title == "Главная">
            <@request_list />
        <#elseif title == "Запросы">
            <@my_request_list />
        <#elseif title == "Отклики на запрос">
            <@respondent_list />
        <#else>
            <@workout_list />
        </#if>
    </div>

    <script>
        const contextPath = "${contextPath}";
    </script>
    <script src="${contextPath}/js/fragments/sidebar.js"></script>
    <script src="${contextPath}/js/fragments/notifications.js"></script>
    <script src="${contextPath}/js/processingRequestButtons.js"></script>
    <script src="${contextPath}/js/processingWorkoutButtons.js"></script>

</#macro>

<#include "/WEB-INF/templates/base.ftl">
