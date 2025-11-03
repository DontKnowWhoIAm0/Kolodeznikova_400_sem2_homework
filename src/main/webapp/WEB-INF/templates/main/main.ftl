<#assign pageCss=["css/fragments/sidebar.css", "css/fragments/notifications.css"]>

<#macro content>

    <#include "/WEB-INF/templates/fragments/header.ftl">
    <#include "/WEB-INF/templates/fragments/sidebar.ftl">
    <#include "/WEB-INF/templates/fragments/notifications.ftl">

    <div class="request_list">
        <#if requests?has_content>
            <#list requests as request>
                <#assign creator = creatorsMap[request.creatorId?string]!>
                <#include "request_item.ftl">
            </#list>
        <#else>
            <p>Активных запросов нет.</p>
        </#if>
    </div>

    <a href="${contextPath}/createRequest" class="new-request">➕</a>

    <script src="${contextPath}/js/fragments/sidebar.js"></script>
    <script src="${contextPath}/js/fragments/notifications.js"></script>

</#macro>

<#include "/WEB-INF/templates/base.ftl">

