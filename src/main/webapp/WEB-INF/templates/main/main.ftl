<#assign pageCss=["css/fragments/sidebar.css", "css/fragments/notifications.css", "css/fragments/header.css", "css/request_item.css", "css/main.css"]>

<#include "/WEB-INF/templates/main/all_requests_list.ftl">
<#include "/WEB-INF/templates/main/user_requests_list.ftl">
<#include "/WEB-INF/templates/main/workouts_list.ftl">

<#macro content>

    <#include "/WEB-INF/templates/fragments/header.ftl">
    <#include "/WEB-INF/templates/fragments/sidebar.ftl">
    <#include "/WEB-INF/templates/fragments/notifications.ftl">

    <div class="auth-container">
        <#if title == "Главная">
            <@request_list />
        <#else>
            <#if title == "Запросы">
                <@my_request_list />
             <#else>
                <@workout_list />
            </#if>
        </#if>
    </div>

    <script>
        const contextPath = "${contextPath}";
    </script>
    <script src="${contextPath}/js/fragments/sidebar.js"></script>
    <script src="${contextPath}/js/fragments/notifications.js"></script>
    <script src="${contextPath}/js/processingApproveButton.js"></script>

</#macro>

<#include "/WEB-INF/templates/base.ftl">
