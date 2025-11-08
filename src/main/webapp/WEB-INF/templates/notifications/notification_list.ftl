<#assign pageCss=["css/fragments/back_button.css", "css/notification.css"]>

<#macro content>
    <a href="javascript:history.back()"><</a>

    <div class="notification_list">
        <#if notifications?has_content>
            <#list notifications as notification>
                <#include "notification_item.ftl">
            </#list>
        <#else>
            <p>Пока что нет уведомлений.</p>
        </#if>
    </div>

</#macro>

<#include "/WEB-INF/templates/base.ftl">